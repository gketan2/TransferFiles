package com.k10.transferfiles.ui.main

import android.os.Bundle
import android.os.Environment
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.k10.transferfiles.databinding.ActivityMainBinding
import com.k10.transferfiles.models.FileObject
import com.k10.transferfiles.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(), FileListCommunicator {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fileListAdapter: FileListAdapter
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecycler()
        println(getExternalFilesDir("/")?.canonicalPath)
        println(Environment.getExternalStorageDirectory().canonicalPath)
    }

    private fun setUpRecycler() {
        fileListAdapter = FileListAdapter(this)
        binding.fileListRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = fileListAdapter
        }
    }

    override fun onFolderClick(fileObject: FileObject) {
    }

    override fun onFileClick(fileObject: FileObject) {
    }
}