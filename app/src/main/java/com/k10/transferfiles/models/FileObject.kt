package com.k10.transferfiles.models

import com.k10.transferfiles.utils.FileType

data class FileObject(
    val name: String,
    val extension: String,
    val fileType: FileType,
    val path: String,
    val isFolder: Boolean,
    val size: Long,
    val formattedSize: String,
    val lastModified: Long,
    val lastModifiedFormatted: String,
    val isHidden: Boolean = false,
    val isAccessible: Boolean = true
)
