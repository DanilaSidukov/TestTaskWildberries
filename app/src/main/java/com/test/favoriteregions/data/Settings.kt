package com.test.favoriteregions.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class Settings(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences("regions", Context.MODE_PRIVATE)

    var regions: Map<String, Boolean> = mapOf()
        get() = preferences.getString("regions", "")?.decode() ?: mapOf()
        set(value) {
            field = value
            preferences.edit {
                putString("regions", field.encode())
            }
        }

    private fun String.decode(): Map<String, Boolean> = split(", ").associate {
        val key = it.substringBefore(":")
        val value = it.substringAfter(":").toBoolean()
        key to value
    }

    private fun Map<String, Boolean>.encode(): String = map {
        "${it.key}:${it.value}"
    }.joinToString(", ")
}