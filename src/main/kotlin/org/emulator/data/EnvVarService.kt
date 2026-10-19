package org.emulator.data

interface EnvVarService {
    fun envValue(name: String): String?
}