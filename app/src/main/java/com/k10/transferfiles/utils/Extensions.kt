package com.k10.transferfiles.utils

import android.net.Uri
import android.view.View
import android.view.View.VISIBLE
import com.k10.transferfiles.models.FileObject
import java.io.File
import java.math.RoundingMode.UP
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date

object Extensions {
    private fun Long.getFileSize(): String {
        if (this < 1024)
            return "$this B"
        var size = this.toDouble()
        size /= 1024
        if (size < 1024)
            return "${size.trimTwoDecimalPoint()} KB"
        size /= 1024
        if (size < 1024)
            return "${size.trimTwoDecimalPoint()} MB"
        size /= 1024
        return "${size.trimTwoDecimalPoint()} GB"
    }

    fun File.getSize(showHidden: Boolean): Pair<Long, String> {
        return if (this.isDirectory) {
            val length = this.listFiles()
                ?.filter { file -> !(!showHidden && file.isHidden) }?.size
                ?: -1
            if (length > 1) Pair(length.toLong(), "$length items") else Pair(
                length.toLong(),
                "$length item"
            )
        } else {
            Pair(
                this.length(),
                this.length().getFileSize()
            )
        }
    }

    fun Long.formatTime(formatter: SimpleDateFormat): String {
        return formatter.format(Date(this))
    }

    fun FileObject.getIcon(packageName: String): Uri {
        val v = when (this.fileType) {
            FileType.FOLDER -> {
                Uri.parse("android.resource://$packageName/drawable/ic_folder_24")
            }

            FileType.TEXT -> {
                Uri.parse("android.resource://$packageName/drawable/ic_text_24")
            }

            FileType.PDF -> {
                Uri.parse("android.resource://$packageName/drawable/ic_pdf_24")
            }

            FileType.DOC -> {
                Uri.parse("android.resource://$packageName/drawable/ic_doc_24")
            }

            FileType.EXCEL -> {
                Uri.parse("android.resource://$packageName/drawable/ic_xls_24")
            }

            FileType.IMAGE -> {
                Uri.fromFile(File(this.path))
            }

            FileType.AUDIO -> {
                Uri.parse("android.resource://$packageName/drawable/ic_audio_24")
            }

            FileType.VIDEO -> {
                Uri.fromFile(File(this.path))
//                Uri.parse("android.resource://$packageName/drawable/ic_video_24")
            }

            FileType.UNKNOWN -> {
                Uri.parse("android.resource://$packageName/drawable/ic_unknown_24")
            }
        }
        return v
    }

    fun Double.trimTwoDecimalPoint(): String {
        val df = DecimalFormat("#.##").apply {
            roundingMode = UP
        }
        return df.format(this)
    }

    var View.visible: Boolean
        get() = visibility == VISIBLE
        set(value) {
            visibility = if (value) VISIBLE else View.GONE
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