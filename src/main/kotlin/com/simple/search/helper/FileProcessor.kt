package com.simple.search.helper

import com.simple.search.dto.Rank
import com.simple.search.service.Dictionary
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import java.util.concurrent.Callable
import java.util.stream.Collectors

/**
 * This class process individual file and stores it's content.
 */
class FileProcessor(private val absolutePath: Path) : Callable<MutableMap<String, Rank>> {

    @Throws(Exception::class)
    override fun call(): MutableMap<String, Rank> {
        val fileName = absolutePath.fileName.toString()
        val fileMap: MutableMap<String, Rank> = HashMap<String, Rank>()
        val rank = Rank()
        val content: MutableList<String> = ArrayList()
        Files.lines(absolutePath).forEach { line: String ->
            val words = line.split(" ").toTypedArray()
            content.addAll(toLowerCase(words))
        }
        val root = Dictionary()
        for (data in content) {
            root.insert(data)
        }
        rank.fileData = content
        rank.dictionary = root
        fileMap[fileName] = rank
        return fileMap
    }

    private fun toLowerCase(words: Array<String>): List<String> {
        return Arrays.stream(words).map { m: String -> m.toLowerCase() }
            .collect(Collectors.toList())
    }
}
