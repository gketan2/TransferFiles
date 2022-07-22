package com.k10.transferfiles.models.mapper

import com.k10.transferfiles.models.FileObject
import com.k10.transferfiles.utils.Extensions.getSize
import com.k10.transferfiles.utils.FileOperations
import com.k10.transferfiles.utils.FileType
import java.io.File

object FileObjectMapper {
    fun fileToFileObject(file: File, showHidden: Boolean = false): FileObject{
        val nameAndExtension = FileOperations.getFileNameAndExtension(file.name, file.isDirectory)
        val sizeAndLength = file.getSize(showHidden)
        return FileObject(
            name = nameAndExtension.first,
            extension = nameAndExtension.second,
            fileType = if (file.isDirectory) FileType.FOLDER
            else FileType.getFileTypeFromExtension(nameAndExtension.second),
            path = file.canonicalPath,
            isFolder = file.isDirectory,
            size = if(sizeAndLength.first == -1L) 0L else sizeAndLength.first,
            formattedSize = if(sizeAndLength.first == -1L) "" else sizeAndLength.second,
            isHidden = file.isHidden,
            isAccessible = sizeAndLength.first != -1L
        )
    }
}