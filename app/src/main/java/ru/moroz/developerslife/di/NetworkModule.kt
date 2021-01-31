package ru.moroz.developerslife.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.moroz.developerslife.BuildConfig
import ru.moroz.developerslife.data.network.RestApi
import ru.moroz.developerslife.mvp.models.LatestPostsModel
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): RestApi = retrofit.create(RestApi::class.java)

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providesLatestModel(api: RestApi): LatestPostsModel = LatestPostsModel(api)
}