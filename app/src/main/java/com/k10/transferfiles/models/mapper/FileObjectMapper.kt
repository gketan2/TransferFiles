package com.k10.transferfiles.models.mapper

import com.k10.transferfiles.models.FileObject
import com.k10.transferfiles.utils.Extensions.getFileSize
import com.k10.transferfiles.utils.FileOperations
import com.k10.transferfiles.utils.FileType
import java.io.File

object FileObjectMapper {
    fun fileToFileObject(file: File): FileObject{
        val nameAndextenstion = FileOperations.getFileNameAndExtension(file.name, file.isDirectory)
        return FileObject(
            nameAndextenstion.first,//files[i].nameWithoutExtension,
            nameAndextenstion.second,//files[i].extension,
            if (file.isDirectory) FileType.FOLDER
            else FileType.getFileTypeFromExtension(nameAndextenstion.second),//files[i].extension),
            file.canonicalPath,
            file.isDirectory,
            if (file.isDirectory) 0 else file.length(), //in bytes
            if (file.isDirectory) "" else file.length().getFileSize(),
            isHidden = file.isHidden
        )
    }
}