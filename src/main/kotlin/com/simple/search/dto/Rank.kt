package com.simple.search.dto

import com.simple.search.service.Dictionary

/**
 * Rank class for holding the list of words and it's associated dictionary for word searching
 */
data class Rank(
    var fileData: List<String>? = null,
    var dictionary: Dictionary? = null
)