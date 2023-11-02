package com.test.favoriteregions.di

import android.content.Context
import com.test.favoriteregions.data.Settings
import com.test.favoriteregions.domain.ApiService
import com.test.favoriteregions.data.RegionRepository
import com.test.favoriteregions.ui.RegionViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class Injector(context: Context) {

    private val settings: Settings = Settings(context)

    private val regionRepository: RegionRepository = RegionRepository(apiService)

    fun injectViewModel() = RegionViewModel(
        regionRepository,
        settings
    )

    companion object {
        private const val BASE_URL = "https://vmeste.wildberries.ru/api/"

        private val apiService: ApiService = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

}