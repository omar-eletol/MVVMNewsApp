package com.androiddevs.mvvmnewsapp.core.entities

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)