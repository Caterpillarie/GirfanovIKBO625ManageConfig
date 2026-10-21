package org.emulator.domain

import org.emulator.data.AppConfig

class ConfigParseUseCase() {
    operator fun invoke(args: Array<String>): AppConfig {
        val scriptPath = (args.indexOfFirst { it == "--script" } + 1)
            .let { if (it == 0 || it >= args.size) "" else args[it] }
        val vfsPath = (args.indexOfFirst { it == "--vfs" } + 1)
        .let { if (it == 0 || it >= args.size) "" else args[it] }
        return AppConfig(scriptPath, vfsPath)
    }
}