package com.narutoshi.minimaldiary

import java.text.SimpleDateFormat
import java.util.*

fun getStringToday() : String {
    return SimpleDateFormat("yyyy/MM/dd").format(Date())
}