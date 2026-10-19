package org.emulator.domain

import org.emulator.data.Command
import org.emulator.data.CommandHandler
import org.emulator.data.CommandResult

class CommandExecuteUseCase(
    private val expander: EnvVarExpandUseCase,
    private val handler: CommandHandler
) {
    fun execute(input: String): CommandResult {
        val expanded = expander.expand(input)
        val parts = expanded.trim().split(Regex("\\s+"))
        if (parts[0].isBlank()) return CommandResult.Error("No command entered")
        val command = Command(parts[0], parts.drop(1))
        return handler.handle(command)
    }
}