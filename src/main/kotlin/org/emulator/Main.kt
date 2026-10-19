package org.emulator

import org.emulator.domain.CommandExecuteUseCase
import org.emulator.domain.EnvVarExpandUseCase
import org.emulator.data.UI
import org.emulator.data.CommandResult
import org.emulator.adapters.CommandHandlerImpl
import org.emulator.adapters.ConsoleUI
import org.emulator.adapters.SystemEnvService

fun main() {
    val ui: UI = ConsoleUI()
    val commandExecutor = CommandExecuteUseCase(
        expander = EnvVarExpandUseCase(
            SystemEnvService(),
        ),
        handler = CommandHandlerImpl(),
    )
    ui.printOutput("Started. Type 'exit' to quit\n")
    while (true) {
        val input = ui.input()
        val commandResult = input?.let {
            commandExecutor.execute(it)
        } ?: CommandResult.Error("Input is null")
        when (commandResult) {
            CommandResult.Exit -> {
                ui.printOutput("Exiting...\n")
                break
            }
            is CommandResult.Success -> ui.printOutput("${commandResult.message}\n")
            is CommandResult.Error -> ui.printOutput("${commandResult.message}\n")
        }
    }
}