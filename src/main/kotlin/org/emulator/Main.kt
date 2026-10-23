package org.emulator

import org.emulator.domain.CommandExecuteUseCase
import org.emulator.domain.EnvVarExpandUseCase
import org.emulator.data.UI
import org.emulator.data.CommandResult
import org.emulator.adapters.CommandHandlerImpl
import org.emulator.adapters.ConsoleInputSource
import org.emulator.adapters.ConsoleUI
import org.emulator.adapters.FileInputSource
import org.emulator.adapters.ScriptRunnerImpl
import org.emulator.adapters.SystemEnvService
import org.emulator.adapters.VfsService
import org.emulator.data.InputSource
import org.emulator.data.ScriptRunner
import org.emulator.domain.ConfigParseUseCase
import org.emulator.domain.ScriptCheckUseCase

fun main(args: Array<String>) {
    val initialSource: InputSource = ConsoleInputSource()
    val configParser = ConfigParseUseCase()
    val appConfig = configParser(args)
    val vfsService = try {
        VfsService(
            initialZipPath = appConfig.vfsPath,
            defaultZipPath = "test.zip"
        )
    } catch (e: Exception) {
        ConsoleUI(initialSource).printOutput("Error: vfs is not found\n")
        return
    }
    val ui: UI = ConsoleUI(initialSource, vfsService::currentPath)
    ui.printOutput("Started. Type 'exit' to quit\n")
    val commandExecutor = CommandExecuteUseCase(
        expander = EnvVarExpandUseCase(
            SystemEnvService(),
        ),
        handler = CommandHandlerImpl(
            vfsService = vfsService
        )
    )
    appConfig.scriptPath.let {
        when (ScriptCheckUseCase()(it)) {
            true -> {
                val scriptRunner: ScriptRunner = ScriptRunnerImpl(ui)
                scriptRunner.run(it)
            }
            false -> {
                if (it.isNotBlank()) ui.printOutput("Script couldn't open\n")
            }
        }
    }
    while (true) {
        val input = ui.input()
        if (ui.inputSource is FileInputSource) {
            if (input == null) {
                ui.inputSource = initialSource
                ui.printOutput("Script ended\n")
                continue
            }
            ui.printOutput("${input}\n")
        }
        val commandResult = input?.let {
            commandExecutor.invoke(it)
        } ?: CommandResult.Error("Input is null")
        when (commandResult) {
            CommandResult.Ignore -> continue
            CommandResult.Exit -> {
                ui.printOutput("Exiting...\n")
                break
            }
            is CommandResult.Success -> ui.printOutput(
                if (commandResult.message.isNotBlank())
                    "${commandResult.message}\n"
                else
                    ""
            )
            is CommandResult.Error -> ui.printOutput(
                if (commandResult.message.isNotBlank())
                    "${commandResult.message}\n"
                else
                    ""
            )
        }
    }
}