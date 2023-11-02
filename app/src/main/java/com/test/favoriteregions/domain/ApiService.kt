package com.test.favoriteregions.domain

import com.test.favoriteregions.domain.entities.RegionInfo
import retrofit2.http.GET

interface ApiService {

    @GET("guide-service/v1/getBrands")
    suspend fun getRegionData(): RegionInfo

}