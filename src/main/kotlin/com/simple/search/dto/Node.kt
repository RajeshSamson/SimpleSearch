package com.simple.search.dto

data class Node(
    var word: String? = null,
    val childNodes: MutableMap<Char, Node> = mutableMapOf()
)