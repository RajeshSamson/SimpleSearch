package com.simple.search.dto

/**
 * Node class which is holds the Word and it's children
 */
data class Node(
    var word: String? = null,
    val childNodes: MutableMap<Char, Node> = mutableMapOf()
)