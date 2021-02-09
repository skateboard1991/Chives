package com.skateboard.coinsplugin.util

import com.google.gson.Gson
import java.lang.reflect.Type

object GsonUtil {

    private val gson = Gson()

    fun <T> fromJson(json: String, type: Type): T = gson.fromJson(json, type)

}