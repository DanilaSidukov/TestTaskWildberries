package com.test.favoriteregions.data

import com.test.favoriteregions.domain.ApiService

class RegionRepository(
    private val apiService: ApiService
) {

    suspend fun getRegionInfo() = apiService.getRegionData()

}