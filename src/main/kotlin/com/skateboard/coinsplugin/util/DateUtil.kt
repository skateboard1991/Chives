package com.skateboard.coinsplugin.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    fun formatDate(timeStamp: String): String {
        val simpleDateFormat = SimpleDateFormat("HH:mm:ss")
        return simpleDateFormat.format(Date(timeStamp.toLong() * 1000L))
    }

}