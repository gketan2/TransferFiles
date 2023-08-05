package com.k10.transferfiles.models

data class FileListObject(
    val pathList: List<String>,
    val path: String,
    val files: List<FileObject>
)
