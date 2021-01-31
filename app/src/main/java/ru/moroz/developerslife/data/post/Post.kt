package ru.moroz.developerslife.data.post

import com.google.gson.annotations.SerializedName

/**
 * Entity for post
 */
data class Post(
    val description: String?,
    @SerializedName("gifURL")
    val url: String?
)