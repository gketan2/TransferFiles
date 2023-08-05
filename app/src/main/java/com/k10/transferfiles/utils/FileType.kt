package com.k10.transferfiles.utils

enum class FileType {
    FOLDER,
    TEXT,  // txt
    PDF,   // pdf
    DOC,   // doc, docx
    EXCEL, // xls, xlsx
    IMAGE, // jpg, jpeg, png
    AUDIO, // mp3, wav,
    VIDEO, // mp4, mkv, mov
    UNKNOWN;

    companion object {
        fun getFileTypeFromExtension(extension: String): FileType {
            return when (extension) {
                "mp3", "wav" -> AUDIO
                "jpg", "jpeg", "png" -> IMAGE
                "mp4", "mkv", "mov" -> VIDEO
                "txt" -> TEXT
                "pdf" -> PDF
                "doc", "docx" -> DOC
                "xls", "xlsx" -> EXCEL
                else -> UNKNOWN
            }
        }
    }
}