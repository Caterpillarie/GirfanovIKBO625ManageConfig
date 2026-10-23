package org.emulator.adapters

import org.emulator.data.VfsDirectory
import org.emulator.data.VfsState
import java.io.File

class VfsService(private val initialZipPath: String = "", private val defaultZipPath: String) {
    private val loader = ZipVfsLoader()
    var state: VfsState = loadInitialVfs(initialZipPath.ifBlank { defaultZipPath })
        private set

    val currentPath: String get() {
        var current = state.currentDirectory
        if (current.dirName == "/") return "/"
        val list = mutableListOf(state.currentDirectory)
        while (current.parent?.dirName != "/") {
            list.add(current.parent!!)
            current = current.parent!!
        }
        return list.foldRight("") { directory, acc -> "$acc/${directory.dirName}" }
    }

    private fun loadInitialVfs(zipPath: String): VfsState {
        val root = loader.load(File(zipPath))
        return VfsState(root, root)
    }

    fun reset() {
        state = loadInitialVfs(defaultZipPath)
    }

    private fun find(path: String): VfsDirectory? {
        var current = if (path.startsWith('/')) {
            state.root
        } else {
            state.currentDirectory
        }
        val segments = path.split('/').filter { it.isNotEmpty() }
        for (segment in segments) {
            if (segment == "..") {
                current = current.parent ?: current
                continue
            }
            val nextNode = current.children[segment]
            if (nextNode is VfsDirectory) {
                current = nextNode
            } else {
                return null
            }
        }
        return current
    }

    fun listDir(path: String): List<String>? = find(path)?.children?.map {
        if (it.value is VfsDirectory) "${it.key}/" else it.key
    }?.sorted()

    fun changeDirectory(path: String): Boolean {
        val current = find(path)
        current?.let {
            state.currentDirectory = current
        } ?: return false
        return true
    }
}