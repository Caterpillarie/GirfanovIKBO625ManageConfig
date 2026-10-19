package org.emulator.data

import org.emulator.data.CommandResult

interface CommandHandler {
    fun handle(command: Command): CommandResult
}