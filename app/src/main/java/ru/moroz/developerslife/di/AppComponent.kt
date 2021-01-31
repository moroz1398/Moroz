package ru.moroz.developerslife.di

import dagger.Component
import ru.moroz.developerslife.ui.fragments.LatestPostsFragment

import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {
    fun inject(latestPostsFragment: LatestPostsFragment)
}
