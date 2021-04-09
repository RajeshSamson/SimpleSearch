package com.simple.search.service

import com.simple.search.dto.Rank
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.nio.file.Path
import java.nio.file.Paths.get
import kotlin.test.assertTrue


class SearchServiceTest {

    private lateinit var searchService: SearchService

    @BeforeEach
    internal fun setUp() {
        searchService = SearchService()
    }

    @Test
    fun `Given source directory should return all file paths`() {
        val filePaths = searchService.findFilePaths(mockSourceDir() + "/mock")
        assertEquals(filePaths!!.size, 2)
        assertEquals(filePaths[0], get(mockSourceDir() + "/mock/file1.txt").toAbsolutePath())
    }

    @Test
    fun `Given file paths should return mapped details`() {
        val fileContent = mockFileContent()
        val rank = fileContent["file1.txt"]
        assertEquals(rank!!.fileData, listOf("mock", "data", "inserted", "for", "testing"))
        val dictionary = rank.dictionary
        assertTrue(dictionary!!.search("mock"))
    }

    @Test
    fun `Given file content and search criteria should return search ranking`() {
        val mockFileContent = mockFileContent()
        val calculateRank =
            searchService.calculateRank(mockFileContent, listOf("kotlin", "program"))
        assertEquals(calculateRank["file2.txt"], 100)
    }

    private fun mockSourceDir(): String {
        val resourceDirectory = get("src", "test", "resources")
        return resourceDirectory.toFile().absolutePath
    }

    private fun mockFileContent(): Map<String, Rank> {
        val paths = listOf<Path>(
            Path.of(mockSourceDir() + "/mock/file1.txt"),
            Path.of(mockSourceDir() + "/mock/file2.txt")
        )
        return searchService.processFileContent(paths)
    }
}