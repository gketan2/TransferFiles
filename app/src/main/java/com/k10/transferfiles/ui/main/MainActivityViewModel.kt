package com.k10.transferfiles.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.k10.transferfiles.models.FileListObject
import com.k10.transferfiles.models.FileObject
import com.k10.transferfiles.models.mapper.FileObjectMapper
import com.k10.transferfiles.persistence.preference.ConfigPreferenceManager
import com.k10.transferfiles.utils.FileOperations
import com.k10.transferfiles.utils.FileType
import com.k10.transferfiles.utils.ResultWrapper
import com.k10.transferfiles.utils.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    @Named("root_path") private val rootPath: String,
    private val configPreferenceManager: ConfigPreferenceManager
) : ViewModel() {

    private var currentPath = ""
    private val _fileListLiveData: MediatorLiveData<ResultWrapper<FileListObject>> =
        MediatorLiveData()
    val fileListLiveData: LiveData<ResultWrapper<FileListObject>> = _fileListLiveData

    /**
     * Fetch the list of files in the given path and update the live data.
     * Include Sorting and Filtering hidden files based on config in shared preference.
     * Which can be updated by user through UI.
     *
     * @param  path
     *         path of directory to get file list
     */
    fun getFilesInPath(path: String = rootPath) {
        viewModelScope.launch(IO) {

            _fileListLiveData.postValue(ResultWrapper.loading(_fileListLiveData.value?.data))

            val result: ArrayList<FileObject> = ArrayList()

            //adding ..
            if (path != rootPath) {
                result.add(
                    FileObject("..", "", FileType.UNKNOWN, "$rootPath/..", true, 0, "")
                )
            }
            val files = File(path).listFiles()
            if (files != null) {
                //TODO should replace with showHiddenFile if not changes from anywhere else
                val configs = configPreferenceManager.getConfigPreference()
                //sort according to preference
                files.sortWith(FileOperations.getComparatorForFileSorting(SortType.HIDDEN))
                for (i in files.indices) {
                    //skipping hidden files based on preference
                    if (!configs.showHidden && files[i].isHidden)
                        continue

                    result.add(FileObjectMapper.fileToFileObject(files[i]))
                }
                _fileListLiveData.postValue(ResultWrapper.success(FileListObject(path, result)))
                //current path is also stored in live data, but is stored in variable to access directly in code
                currentPath = File(path).canonicalPath
            } else {
                //not a folder or no read access
                _fileListLiveData.postValue(
                    ResultWrapper.failed(
                        _fileListLiveData.value?.data,
                        "not a folder or no read access."
                    )
                )
            }
        }
    }

    var showHiddenFile: Boolean = configPreferenceManager.getConfigPreference().showHidden
        set(value) {
            configPreferenceManager.setShowHiddenFiles(value)
            getFilesInPath(currentPath)
            field = value
        }

    /**
     * Shows files in parent folder, if it is root path,
     * activity will handle the back press.
     *
     * @return  Boolean
     *
     *          true, if back press handled by viewModel
     *          false, if activity needs to handle back press
     */
    fun onBackPress(): Boolean {
        if (currentPath == rootPath) {
            return false
        }
        getFilesInPath("$currentPath/..")
        return true
    }

}