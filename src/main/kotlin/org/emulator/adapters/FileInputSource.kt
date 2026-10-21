package org.emulator.adapters

import org.emulator.data.InputSource
import java.io.BufferedReader

class FileInputSource(private val reader: BufferedReader): InputSource {
    override fun nextLine(): String? = reader.readLine()
}