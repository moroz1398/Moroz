package ru.moroz.developerslife.data.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/** @SelfDocumented */
interface RestApi {
    @GET("{category}/{page}?json=true")
    fun getPosts(
        @Path("category") category: String,
        @Path("page") page: Int
    ): Call<ResponseApi>
}