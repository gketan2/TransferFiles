package com.k10.transferfiles.utils

import java.math.RoundingMode.UP
import java.text.DecimalFormat

object Extensions {
    fun Long.getFileSize(): String {
        if (this < 1024)
            return "${this}B"
        var size = this.toDouble()
        size /= 1024
        if (size < 1024)
            return "${size.trimTwoDecimalPoint()}KB"
        size /= 1024
        if (size < 1024)
            return "${size.trimTwoDecimalPoint()}MB"
        size /= 1024
        return "${size.trimTwoDecimalPoint()}GB"
    }

    fun Double.trimTwoDecimalPoint(): String {
        val df = DecimalFormat("#.##").apply {
            roundingMode = UP
        }
        return df.format(this)
    }

    fun String.isHiddenFile(): Boolean {
        return this.split(".")[0].isEmpty()
    }

    fun <T> List<T>.print() {
        println("-------")
        this.forEach {
            println(it)
        }
        println("-------")
    }

    fun <T> Array<T>.print() {
        println("-------")
        this.forEach {
            println(it)
        }
        println("-------")
    }
}