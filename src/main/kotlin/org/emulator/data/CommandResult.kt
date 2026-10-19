package org.emulator.data

sealed interface CommandResult {
    data class Success(val message: String) : CommandResult
    data class Error(val message: String) : CommandResult
    data object Exit : CommandResult
}