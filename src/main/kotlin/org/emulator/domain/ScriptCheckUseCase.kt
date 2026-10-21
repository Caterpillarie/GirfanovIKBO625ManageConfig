package org.emulator.domain

import java.nio.file.Files
import java.nio.file.Paths

class ScriptCheckUseCase {
    operator fun invoke(scriptPath: String): Boolean =
        try {
            val path = Paths.get(scriptPath)
            Files.isRegularFile(path)
        } catch (_: Exception) {
            false
        }
}