package org.emulator.adapters

import org.emulator.data.EnvVarService

class SystemEnvService : EnvVarService {
    override fun envValue(name: String): String? =
        System.getenv(name)
}