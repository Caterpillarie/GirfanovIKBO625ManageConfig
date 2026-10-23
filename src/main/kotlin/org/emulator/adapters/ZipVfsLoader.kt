package org.emulator.adapters

import org.emulator.data.VfsDirectory
import org.emulator.data.VfsFile
import java.io.File
import java.util.Base64
import java.util.zip.ZipInputStream

class ZipVfsLoader {
    fun load(zipFile: File): VfsDirectory {
        val root = VfsDirectory("/")
        ZipInputStream(zipFile.inputStream()).use { zipInputStream ->
            var entry = zipInputStream.nextEntry
            while(entry != null) {
            val path = entry.name
                if (entry.isDirectory) {
                    addToTree(root, path)
                } else {
                    val bytes = zipInputStream.readBytes()
                    val content = if (isBinary(path)) {
                        Base64.getEncoder().encodeToString(bytes)
                    } else {
                        String(bytes, Charsets.UTF_8)
                    }
                    addToTree(
                        root,
                        path,
                        file = VfsFile(path.substringAfterLast('/'), content)
                    )
                }
                entry = zipInputStream.nextEntry
            }
        }
        return root
    }

    private fun isBinary(path: String): Boolean = when(path.substringAfterLast('.')) {
        "txt" -> false
        else -> true
    }

    private fun addToTree(root: VfsDirectory, path: String, file: VfsFile? = null) {
        val parts = path.split("/").filter { it.isNotEmpty() }
        var current = root
        for((i, part) in parts.withIndex()) {
            if(file != null && i == parts.size - 1) break
            current = current.children.getOrPut(part) {
                VfsDirectory(part, current)
            } as VfsDirectory
        }
        file?.let {
            val fileName = parts.last()
            current.children[fileName] = file
        }
    }
}