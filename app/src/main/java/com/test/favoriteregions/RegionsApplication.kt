package com.test.favoriteregions

import android.app.Application
import com.test.favoriteregions.di.Injector

class RegionsApplication : Application() {

    companion object {
        lateinit var injector: Injector
    }

    override fun onCreate() {
        super.onCreate()
        injector = Injector(applicationContext)
    }
}