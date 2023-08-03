package com.k10.transferfiles.utils

enum class FileType {
    FOLDER,
    TEXT,  // txt, doc, docx
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
                "txt", "doc", "docx" -> TEXT
                else -> UNKNOWN
            }
        }
    }
}