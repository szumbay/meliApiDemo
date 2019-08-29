package com.example.meliapisdemo.utils

import android.content.Context
import android.content.SharedPreferences


class Prefs (context: Context) {
    val PREFS_NAME = "com.example.meliapisdemo"
    val SEARCH_NAME = "shared_name"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)


    fun lastSearch(value: String){
        prefs.edit().putString(SEARCH_NAME, value).apply()
    }
    fun lastSearch() : String{
        return  prefs.getString(SEARCH_NAME, "")
    }
}
