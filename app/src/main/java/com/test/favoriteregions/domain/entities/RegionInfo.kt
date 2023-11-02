package com.test.favoriteregions.domain.entities

import com.google.gson.annotations.SerializedName

data class RegionInfo(
    @SerializedName("brands")
    val brands: List<Brand>
)