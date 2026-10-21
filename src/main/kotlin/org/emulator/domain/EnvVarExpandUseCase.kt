package org.emulator.domain

import org.emulator.data.EnvVarService

class EnvVarExpandUseCase(
    private val envService: EnvVarService
) {
    operator fun invoke(input: String): String =
        Regex("\\$(\\w+)").replace(input) { matchResult ->
            envService.envValue(matchResult.groupValues[1]) ?: matchResult.value
    }
}