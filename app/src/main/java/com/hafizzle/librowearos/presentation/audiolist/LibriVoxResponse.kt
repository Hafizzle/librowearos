package com.hafizzle.librowearos.presentation.audiolist

data class LibriVoxResponse(
    val books: List<Book>
)

data class Book(
    val id: Int,
    val title: String,
    val url_text_source: String
)
