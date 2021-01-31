package ru.moroz.developerslife.mvp.models

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.moroz.developerslife.data.network.ResponseApi
import ru.moroz.developerslife.data.network.RestApi
import ru.moroz.developerslife.data.post.Post
import ru.moroz.developerslife.throwables.NoDataThrowable
import ru.moroz.developerslife.throwables.NoMoreDataThrowable
import javax.inject.Inject

private const val postCategory = "latest"

/**
 * Model for "Latest post" screen
 */
class LatestPostsModel @Inject constructor(private val api: RestApi) {

    private val latestPosts = mutableListOf<Post>()
    private var latestPostsPageIdx = 0
    private var totalCount = 0

    /**
     * Get latest post
     * @param onPostLoaded callback for loaded post
     * @param throwableHandler callback for throwable
     * @param currentPostIndex current post index
     */
    fun getLatestPost(
        onPostLoaded: (Post?) -> Unit,
        throwableHandler: (Throwable) -> Unit,
        currentPostIndex: Int
    ) {
        if (latestPosts.isEmpty()) {
            loadPosts(onPostLoaded, throwableHandler)
        } else {
            getPostFromCache(currentPostIndex, onPostLoaded, throwableHandler)
        }
    }

    private fun getPostFromCache(
        postIdx: Int,
        onPostLoaded: (Post?) -> Unit,
        throwableHandler: (Throwable) -> Unit
    ) {
        if (postIdx >= latestPosts.size) {
            loadNextLatestPosts(onPostLoaded, throwableHandler)
            return
        }

        onPostLoaded.invoke(latestPosts[postIdx])
    }

    private fun loadNextLatestPosts(
        onPostLoaded: (Post?) -> Unit,
        throwableHandler: (Throwable) -> Unit
    ) {
        latestPostsPageIdx++
        loadPosts(onPostLoaded, throwableHandler)
    }

    private fun loadPosts(onPostLoaded: (Post?) -> Unit, throwableHandler: (Throwable) -> Unit) {
        api.getPosts(postCategory, latestPostsPageIdx).enqueue(object : Callback<ResponseApi> {
            override fun onResponse(call: Call<ResponseApi>, response: Response<ResponseApi>) {
                val isSuccessfulResponse = response.isSuccessful
                val body = response.body()

                if (isSuccessfulResponse && body != null) {
                    var posts = body.posts
                    val postCount = body.totalCount

                    if (postCount != null) {
                        totalCount = postCount
                    }

                    if (posts.isNullOrEmpty() && totalCount != 0) {
                        latestPostsPageIdx--
                        throwableHandler.invoke(NoMoreDataThrowable())
                        return
                    }

                    if (posts.isNullOrEmpty() && totalCount == 0) {
                        throwableHandler.invoke(NoDataThrowable())
                        return
                    }

                    if (!posts.isNullOrEmpty()) {
                        latestPosts.addAll(posts)
                        onPostLoaded.invoke(posts[0])
                    }

                } else {
                    throwableHandler.invoke(UnknownError())
                }
            }

            override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                throwableHandler.invoke(t)
            }
        })
    }
}