package com.test.favoriteregions.domain.entities

import com.google.gson.annotations.SerializedName

data class Brand(
    @SerializedName("brandId")
    val brandId: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("tagIds")
    val tagIds: List<String>,
    @SerializedName("thumbUrls")
    val thumbUrls: List<String>,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("viewsCount")
    val viewsCount: Int,
    var like: Boolean = false
)