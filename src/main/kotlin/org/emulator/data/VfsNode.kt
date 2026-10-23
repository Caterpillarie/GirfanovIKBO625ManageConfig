package org.emulator.data

sealed class VfsNode(val name: String)

data class VfsFile(
    val fileName: String,
    var content: String
) : VfsNode(fileName)

data class VfsDirectory(
    val dirName: String,
    var parent: VfsDirectory? = null,
    val children: MutableMap<String, VfsNode> = mutableMapOf()
) : VfsNode(dirName)