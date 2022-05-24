package com.k10.transferfiles.utils

enum class FileType {
    FOLDER,
    OTHER,
    AUDIO, // mp3, wav,
    VIDEO, // mp4, mkv, mov
    TEXT;  // txt, doc, docx

    companion object {
        fun getFileTypeFromExtension(extension: String): FileType {
            return when (extension) {
                "mp3", "wav" -> AUDIO
                "mp4", "mkv", "mov" -> VIDEO
                "txt", "doc", "docx" -> TEXT
                else -> OTHER
            }
        }
    }
}