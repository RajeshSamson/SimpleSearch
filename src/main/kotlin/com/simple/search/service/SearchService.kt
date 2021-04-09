package com.simple.search.service

import com.simple.search.dto.Rank
import com.simple.search.helper.FileProcessor
import java.io.IOException
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.util.concurrent.Executors
import java.util.stream.Collectors
import java.util.stream.IntStream
import kotlin.math.roundToInt
import kotlin.streams.toList

/**
 * This class servers a Service layer for performing various operations on the file content.
 */
class SearchService {

    /**
     * This functions takes the source directory and collects all the absolute path located
     * source directory.
     */
    fun findFilePaths(sourceDirectory: String?): List<Path>? {
        val configFilePath = FileSystems.getDefault().getPath(sourceDirectory!!)
        try {
            Files.walk(configFilePath).use { stream ->
                return stream.filter { file: Path -> this.isTextFile(file) }
                    .map { obj: Path -> obj.toAbsolutePath() }
                    .sorted().collect(Collectors.toList())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * This functions filters the text files
     */
    private fun isTextFile(file: Path): Boolean {
        return file.toFile().isFile && file.toFile().name.endsWith(".txt")
    }

    /**
     * This functions generates the a Map which basically holds the filename and the
     * associated content and dictionary.
     */
    fun processFileContent(filePath: List<Path>): Map<String, Rank> {
        val dataContainer: MutableMap<String, Rank> = HashMap()
        val executorService = Executors.newFixedThreadPool(10)
        val fileProcessors =
            filePath.stream().map { path -> FileProcessor(path) }.collect(Collectors.toList())
        val fileData = fileProcessors.stream().map { task -> executorService.submit(task) }
            .collect(Collectors.toList())
        IntStream.range(0, fileData.size).forEach {
            val map = fileData[it].get()
            for ((key, value) in map) {
                dataContainer[key] = value
            }
        }
        executorService.shutdown()
        return dataContainer
    }

    /**
     * This functions takes the search input an calculates percentage based on the number of words
     * found in the file.
     */
    fun calculateRank(
        fileWithContent: Map<String, Rank>,
        searchCriteria: List<String>
    ): Map<String, Int> {
        val results: MutableMap<String, Int> = HashMap()
        fileWithContent.forEach { (fileName, rank) ->
            var count = 0
            val dictionary = rank.dictionary
            for (matcher in searchCriteria) {
                if (dictionary!!.search(matcher)) {
                    count++
                }
            }
            val value = count.toDouble() / searchCriteria.size.toDouble()
            if (value > 0) {
                val percentage = (value * 100).roundToInt()
                results[fileName] = percentage
            }
        }
        return calculateTo10Records(results)
    }

    /**
     * This function sort the calculated ranks.
     */
    private fun calculateTo10Records(results: MutableMap<String, Int>): Map<String, Int> {
        val sortedResult = results.toList().sortedByDescending { (_, value) -> value }.toMap()
        if (sortedResult.size > 10) {
            val limit = sortedResult.entries.stream().limit(10).toList()
            return limit.associate { Pair(it.key, it.value) }
        }
        return sortedResult
    }
}