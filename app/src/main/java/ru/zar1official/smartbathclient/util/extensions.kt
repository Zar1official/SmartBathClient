package ru.zar1official.smartbathclient.util

fun Float.round(decimals: Int = 2): Float = "%.${decimals}f".format(this).toFloat()