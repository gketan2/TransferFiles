package com.k10.transferfiles.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.k10.transferfiles.models.FileObject
import com.k10.transferfiles.utils.Extensions.getFileSize
import com.k10.transferfiles.utils.Extensions.isHiddenFile
import com.k10.transferfiles.utils.FileType
import com.k10.transferfiles.utils.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MainActivityViewModel @Inject constructor(@Named("root_path") private val rootPath: String) :
    ViewModel() {

    private val _fileListLiveData: MediatorLiveData<ResultWrapper<List<FileObject>>> =
        MediatorLiveData()
    val fileListLiveData: LiveData<ResultWrapper<List<FileObject>>> = _fileListLiveData

    fun getFilesInPath(path: String = rootPath) {
        viewModelScope.launch {
            val result: ArrayList<FileObject> = ArrayList()
            result.add(
                FileObject("..", "", FileType.OTHER, "$rootPath/..", true, 0, "")
            )
            val files = File(path).listFiles()
            if (files != null) {
                for (i in 0..files.size) {
                    //file is hidden, will handle later or not
                    if (files[0].name.isHiddenFile())
                        continue
                    //check if filename starts with . (hidden) (file.extension will return filename)
                    /**
                     * conditions:
                     * isHidden : name starts with '.'
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
                     * */
                    result.add(
                        FileObject(
                            files[i].nameWithoutExtension,
                            files[i].extension,
                            FileType.getFileTypeFromExtension(files[i].extension),
                            files[i].canonicalPath,
                            files[i].isDirectory,
                            if (files[i].isDirectory) 0 else files[i].length(), //in bytes
                            if (files[i].isDirectory) "" else files[i].length().getFileSize(),
                            isHidden = files[0].name.isHiddenFile()
                        )
                    )
                }
            } else {
                //not a folder or no read access
            }
        }
    }

}