package com.connor.composeui.test

import com.connor.composeui.models.data.ChildData
import com.connor.composeui.utils.logCat
import java.io.File

fun main() {
    val phoneNumbers = listOf("1", "2", "3")
    val emails = listOf("4", "5", "6", "8","9")
    maxOf(phoneNumbers.size, emails.size).let {
        (0..<it).asIterable().map { i ->
            ChildData(
                phoneNumbers.getOrElse(i) { "" },
                emails.getOrElse(i) { "" }
            )
        }
    }.also {
        println(it)
    }
}

fun copyTest() {
    val root = "/Users/lothric/Downloads/test/"
    val test = mutableMapOf(
        File(root + "s/image") to File(root + "d/"),
    )
    test[File(root + "s/about")] = File(root + "d/")
    val te = listOf(File(root + "d/test"), File(root + "d/image"))
    te.forEach {
        deleteFolder(it)
    }
    copyFolders(test) {
        println(it)
    }
}

private fun deleteFolder(folder: File) {
    if (!folder.exists()) {
        return
    }

    val files = folder.listFiles() ?: return

    for (file in files) {
        if (file.isDirectory) {
            deleteFolder(file)
        } else {
            file.delete()
        }
    }
    folder.delete()
}

fun copyFolders(files: Map<File, File>, progressCallback: (Int) -> Unit) {
    val totalSourceSize = getTotalSize(files.keys)
    var copySize = 0L
    var hub = 0L

    files.forEach { (source, destination) ->
        copyFolder(source, destination) { size, progress ->
            val currentSize = copySize
            if (progress == 100) {
                hub += size
                copySize = hub
            } else copySize += size - currentSize + hub
            val overallProgress = (copySize.toDouble() / totalSourceSize * 100).toInt()
            progressCallback(overallProgress)
        }
        File(destination, "about").renameTo(File(destination, "test"))
    }
}


private fun copyFolder(
    source: File,
    destination: File,
    progressCallback: (Long, Int) -> Unit
) {
    if (!source.exists() || !source.isDirectory) {
        throw IllegalArgumentException("Source must be a valid directory.")
    }
    if (!destination.exists()) {
        destination.mkdirs()
    }

    val totalSourceSize = getFolderSize(source)

    val stack = mutableListOf<Pair<File, File>>()
    stack.add(Pair(source, destination))
    var copiedSize = 0L
    while (stack.isNotEmpty()) {
        val (sourceDir, destDir) = stack.removeAt(0)
        val sourceFiles = sourceDir.listFiles() ?: continue
        val destSubDir = File(destDir, sourceDir.name)
        destSubDir.mkdirs()
        sourceFiles.forEach { file ->
            val destFile = File(destSubDir, file.name)
            if (file.isDirectory) {
                stack.add(Pair(file, destSubDir))
            } else {
                runCatching { file.copyTo(destFile, overwrite = true) }
                copiedSize += file.length()
                val overallProgress = (copiedSize.toDouble() / totalSourceSize * 100).toInt()
                progressCallback(copiedSize, overallProgress)
            }
        }
    }
}

private fun getTotalSize(folders: Set<File>): Long {
    return folders.sumOf { getFolderSize(it) }
}

private fun getFolderSize(file: File): Long {
    return if (file.isDirectory) {
        val files = file.listFiles()
        files?.sumOf { getFolderSize(it) } ?: 0L
    } else {
        file.length()
    }
}