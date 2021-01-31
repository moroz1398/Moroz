package ru.moroz.developerslife.data.network

import com.google.gson.annotations.SerializedName
import ru.moroz.developerslife.data.post.Post

/**
 * Data class for api response
 */
data class ResponseApi(
    @SerializedName("result") val posts: List<Post>?,
    val totalCount: Int?
)