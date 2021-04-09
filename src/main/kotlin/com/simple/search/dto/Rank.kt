package com.simple.search.dto

import com.simple.search.service.Dictionary

data class Rank(
    var fileData: List<String>? = null,
    var dictionary: Dictionary? = null
)