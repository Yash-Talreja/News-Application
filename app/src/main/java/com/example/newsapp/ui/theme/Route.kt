package com.example.newsapp.ui.theme

import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Serializable
object HomepageScreen


@Serializable
data class NewsArticleScreen(
    val url:String
)