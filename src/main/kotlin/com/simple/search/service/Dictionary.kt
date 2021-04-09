package com.simple.search.service

import com.simple.search.dto.Node

/**
 * Dictionary is trie data structure implementation which is used for inserting and searching
 * a word in the dictionary.
 */
class Dictionary {

    private val root = Node()

    /**
     * This function is used for inserting the word to the dictionary.
     */
    fun insert(word: String) {
        var currentNode = root
        for (char in word) {
            if (currentNode.childNodes[char] == null) {
                currentNode.childNodes[char] = Node()
            }
            currentNode = currentNode.childNodes[char]!!
        }
        currentNode.word = word
    }

    /**
     * This function is used for search the word in the dictionary.
     */
    fun search(word: String): Boolean {
        var currentNode = root
        for (char in word) {
            if (currentNode.childNodes[char] == null) {
                return false
            }
            currentNode = currentNode.childNodes[char]!!
        }
        return currentNode.word != null
    }
}