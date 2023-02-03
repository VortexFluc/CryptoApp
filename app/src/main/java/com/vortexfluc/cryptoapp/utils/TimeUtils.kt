package com.vortexfluc.cryptoapp.utils

import android.icu.util.UniversalTimeScale.toLong
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

fun convertTimestampToTime(timestamp: Int?): String {
    if (timestamp == null) return ""
    val longNum: Long = timestamp.toLong()
    val stamp = Timestamp(longNum * 1000)
    val date = Date(stamp.time)
    val pattern = "HH:mm:ss"

    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    sdf.timeZone = TimeZone.getDefault()
    return sdf.format(date)
}