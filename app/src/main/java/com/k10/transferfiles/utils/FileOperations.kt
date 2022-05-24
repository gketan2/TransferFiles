package com.k10.transferfiles.utils

object FileOperations {

    fun getFileNameAndExtension(fileName: String): Pair<String, String> {
        if (isHidden(fileName)) {
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

    fun isHidden(fileName: String) = fileName[0] == '.'
}