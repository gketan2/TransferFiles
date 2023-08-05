package com.k10.transferfiles.utils

import com.k10.transferfiles.models.FileObject
import java.io.File

object FileOperations {
    /** isHidden : name starts with '.'
     *   generalFilename = .name.extension
     *   edgecase = .name
     *
     *   getFileName(): String
     *   val nameslist = name.split(".")
     *   if (hidden)
     *      if (namelist.size == 2 ) //if hidden first item will always be "" (empty)
     *          //2 means  name = ".filename"
     *          name = namelist[1]
     *          extenstion = ""
     *      else
     *          name = loop from 2 to namelist.size-2 (including)
     *          extension = namelist[namelist.size-1]
     **/
    fun getFileNameAndExtension(fileName: String, isDirectory: Boolean): Pair<String, String> {
        if (isFileHidden(fileName)) {
            if (isDirectory) {
                return Pair(fileName.substring(1, fileName.length), "")
            }
            val extslayer = fileName.lastIndexOf(".")
            if (extslayer == 0) { //no extension
                return Pair(fileName.substring(1, fileName.length), "")
            } else {
                return Pair(
//                    fileName.substring(1, extslayer),
                    fileName,
                    fileName.substring(extslayer + 1, fileName.length)
                )
            }
        } else {
            if (isDirectory) {
                return Pair(fileName, "")
            }
            val extslayer = fileName.lastIndexOf(".")
            return if (extslayer == -1) {
                Pair(fileName, "")
            } else {
                Pair(
//                    fileName.substring(0, extslayer),
                    fileName,
                    fileName.substring(extslayer + 1, fileName.length)
                )
            }
        }
    }

    private fun isFileHidden(fileName: String) = fileName[0] == '.'

    fun getComparatorForFileSorting(
        sortType: SortType,
        reverse: Boolean = false
    ): Comparator<FileObject> {
        return when (sortType) {
            SortType.LAST_MODIFIED -> {
                Comparator { o1, o2 ->
                    if (o2.isFolder && o1.isFolder) {
                        // do sort
                        if (reverse) o2.lastModified.compareTo(o1.lastModified)
                        else o1.lastModified.compareTo(o2.lastModified)
                    } else if (!o2.isFolder && !o1.isFolder) {
                        // do sort
                        if (reverse) o2.lastModified.compareTo(o1.lastModified)
                        else o1.lastModified.compareTo(o2.lastModified)
                    } else {
                        // priority to folder
                        val multiplier = if (reverse) -1 else 1
                        if (o1.isFolder) -1*multiplier else 1*multiplier
                    }
                }
            }
            SortType.SIZE -> {
                Comparator { o1, o2 ->
                    if (o2.isFolder && o1.isFolder) {
                        // do sort
                        if (reverse) o2.size.compareTo(o1.size)
                        else o1.size.compareTo(o2.size)
                    } else if (!o2.isFolder && !o1.isFolder) {
                        // do sort
                        if (reverse) o2.size.compareTo(o1.size)
                        else o1.size.compareTo(o2.size)
                    } else {
                        // priority to folder
                        val multiplier = if (reverse) -1 else 1
                        if (o1.isFolder) -1*multiplier else 1*multiplier
                    }
                }
            }
            SortType.NAME -> {
                Comparator { o1, o2 ->
                    if (o2.isFolder && o1.isFolder) {
                        // do sort
                        if (reverse) o2.name.compareTo(o1.name)
                        else o1.name.compareTo(o2.name)
                    } else if (!o2.isFolder && !o1.isFolder) {
                        // do sort
                        if (reverse) o2.name.compareTo(o1.name)
                        else o1.name.compareTo(o2.name)
                    } else {
                        // priority to folder
                        val multiplier = if (reverse) -1 else 1
                        if (o1.isFolder) -1*multiplier else 1*multiplier
                    }
                }
            }
        }
    }
}