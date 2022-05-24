package com.k10.transferfiles.utils

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
                    fileName.substring(1, extslayer),
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
                    fileName.substring(0, extslayer),
                    fileName.substring(extslayer + 1, fileName.length)
                )
            }
        }
    }

    private fun isFileHidden(fileName: String) = fileName[0] == '.'

    fun getComparatorForFileSorting(
        sortType: SortType,
        reverse: Boolean = false
    ): Comparator<File> {
        return when (sortType) {
            SortType.HIDDEN -> {
                Comparator { o1, o2 ->
                    if (reverse) {
                        if (o1.isHidden && o2.isHidden) 0 else if (!o1.isHidden && !o2.isHidden) 0 else if (o1.isHidden) -1 else 1
                    } else {
                        if (o1.isHidden && o2.isHidden) 0 else if (!o1.isHidden && !o2.isHidden) 0 else if (o1.isHidden) 1 else -1
                    }
                }
            }
            SortType.LAST_MODIFIED -> {
                Comparator { o1, o2 ->
                    if (reverse) o2.lastModified().compareTo(o1.lastModified())
                    else o1.lastModified().compareTo(o2.lastModified())
                }
            }
            SortType.SIZE -> {
                Comparator { o1, o2 ->
                    if (reverse) o2.length().compareTo(o1.length())
                    else o1.length().compareTo(o2.length())
                }
            }
            SortType.NAME -> {
                Comparator { o1, o2 ->
                    if (reverse) o2.nameWithoutExtension.compareTo(o1.nameWithoutExtension)
                    else o1.nameWithoutExtension.compareTo(o2.nameWithoutExtension)
                }
            }
        }
    }
}