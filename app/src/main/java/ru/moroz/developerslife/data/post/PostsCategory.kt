package ru.moroz.developerslife.data.post

/**
 * Enum for post category
 */
enum class PostsCategory(val categoryName: String) {
    LATEST("Последние"),
    HOT("Горячие"),
    TOP("Лучшие")
}