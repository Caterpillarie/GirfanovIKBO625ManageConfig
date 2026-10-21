package org.emulator.adapters

import org.emulator.data.InputSource

class ConsoleInputSource: InputSource {
    override fun nextLine(): String? = readlnOrNull()
}