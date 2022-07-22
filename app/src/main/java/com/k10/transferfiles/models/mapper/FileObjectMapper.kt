package com.k10.transferfiles.models.mapper

import com.k10.transferfiles.models.FileObject
import com.k10.transferfiles.utils.Extensions.getSize
import com.k10.transferfiles.utils.FileOperations
import com.k10.transferfiles.utils.FileType
import java.io.File

object FileObjectMapper {
    fun fileToFileObject(file: File, showHidden: Boolean = false): FileObject{
        val nameAndextenstion = FileOperations.getFileNameAndExtension(file.name, file.isDirectory)
        val sizeAndlength = file.getSize(showHidden)
        return FileObject(
            nameAndextenstion.first,
            nameAndextenstion.second,
            if (file.isDirectory) FileType.FOLDER
            else FileType.getFileTypeFromExtension(nameAndextenstion.second),//files[i].extension),
            file.canonicalPath,
            file.isDirectory,
            sizeAndlength.first, //if (file.isDirectory) file.list()?.let{ return@let it.size.toLong() } ?: 0 else file.length(), //in bytes
            sizeAndlength.second, //if (file.isDirectory) "(" + (file.list()?.let{ return@let it.size } ?: 0) + ")" else file.length().getFileSize(),
            isHidden = file.isHidden
        )
    }
}