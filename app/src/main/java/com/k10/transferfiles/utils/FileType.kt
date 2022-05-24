package com.k10.transferfiles.utils

enum class FileType {
    FOLDER,
    UNKNOWN,
    AUDIO, // mp3, wav,
    IMAGE, // jpg, jpeg, png
    VIDEO, // mp4, mkv, mov
    TEXT;  // txt, doc, docx

    companion object {
        fun getFileTypeFromExtension(extension: String): FileType {
            return when (extension) {
                "mp3", "wav" -> AUDIO
                "jpg", "jpeg", "png" -> IMAGE
                "mp4", "mkv", "mov" -> VIDEO
                "txt", "doc", "docx" -> TEXT
                else -> UNKNOWN
            }
        }
    }
}