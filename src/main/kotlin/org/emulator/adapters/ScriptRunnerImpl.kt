package org.emulator.adapters

import org.emulator.data.ScriptRunner
import org.emulator.data.UI
import java.io.BufferedReader
import java.lang.Exception
import java.nio.file.Files
import java.nio.file.Paths

class ScriptRunnerImpl(val ui: UI): ScriptRunner {
    override fun run(scriptPath: String) {
        try {
            val path = Paths.get(scriptPath)
            val script: BufferedReader = Files.newBufferedReader(path)
            ui.inputSource = FileInputSource(script)
        } catch (e: Exception) {
            e.message?.let { ui.printOutput("$it\n") }
        }
    }
}