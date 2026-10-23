package org.emulator.data

data class VfsState(
    var root: VfsDirectory,
    var currentDirectory: VfsDirectory
)
