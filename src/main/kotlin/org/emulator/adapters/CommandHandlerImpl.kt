package org.emulator.adapters

import org.emulator.data.Command
import org.emulator.data.CommandHandler
import org.emulator.data.CommandResult

class CommandHandlerImpl(private val vfsService: VfsService) : CommandHandler {
    override fun handle(command: Command): CommandResult =
        when(command.name) {
            "ls" -> {
                val path = command.args.firstOrNull() ?: ""
                val files = vfsService.listDir(path)
                when {
                    files?.isEmpty() == true -> CommandResult.Success("")
                    files != null -> CommandResult.Success(files.joinToString(" "))
                    else -> CommandResult.Error("ls: $path: No such directory")
                }
            }
            "cd" -> {
                val path = command.args.firstOrNull() ?: "/"
                if (vfsService.changeDirectory(path))
                    CommandResult.Success("")
                else
                    CommandResult.Error("cd: $path: No such directory")
            }
            "vfs-init" -> {
                vfsService.reset()
                CommandResult.Success("")
            }
            "exit" -> CommandResult.Exit
            else -> CommandResult.Error("${command.name}: command not found")
        }
}