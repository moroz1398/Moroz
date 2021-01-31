package ru.moroz.developerslife.app

import android.app.Application
import ru.moroz.developerslife.di.AppComponent
import ru.moroz.developerslife.di.AppModule
import ru.moroz.developerslife.di.DaggerAppComponent

/**
 * Application class
 */
class App : Application() {
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}