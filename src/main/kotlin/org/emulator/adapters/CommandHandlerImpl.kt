package org.emulator.adapters

import org.emulator.data.Command
import org.emulator.data.CommandHandler
import org.emulator.data.CommandResult

class CommandHandlerImpl : CommandHandler {
    override fun handle(command: Command): CommandResult =
        when(val name = command.name) {
            "ls", "cd" -> CommandResult.Success("$name ${command.args}")
            "exit" -> CommandResult.Exit
            else -> CommandResult.Error("$name command not found")
        }
}