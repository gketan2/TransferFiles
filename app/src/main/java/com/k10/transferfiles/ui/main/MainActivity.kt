package com.k10.transferfiles.ui.main

import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.switchmaterial.SwitchMaterial
import com.k10.transferfiles.R
import com.k10.transferfiles.databinding.ActivityMainBinding
import com.k10.transferfiles.models.FileObject
import com.k10.transferfiles.ui.BaseActivity
import com.k10.transferfiles.utils.Extensions.visible
import com.k10.transferfiles.utils.ResultStatus
import dagger.hilt.android.AndroidEntryPoint
import java.util.Timer
import java.util.TimerTask

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
        viewModel.getFilesInPath()
        viewModel.fileListLiveData.observe(this) {
            when (it.status) {
                ResultStatus.LOADING -> {
                    binding.progressBar.visible = true
                }

                ResultStatus.SUCCESS -> {
                    binding.progressBar.visible = false
                    fileListAdapter.submitList(it.data?.files!!)
                }

                ResultStatus.FAILED -> {
                    binding.progressBar.visible = false
                }
            }
        }

        onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_config_select, menu)
        val hiddenItem = menu.findItem(R.id.show_hidden_files)
        hiddenItem?.setActionView(R.layout.switch_for_menu)

        val hiddenSwitch = hiddenItem?.actionView?.findViewById<SwitchMaterial>(R.id.hidden_switch)
        hiddenSwitch?.isChecked = viewModel.getShowHiddenFiles()

        hiddenSwitch?.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setShowHiddenFiles(isChecked)
        }
        return true
    }

    private fun setUpRecycler() {
        fileListAdapter = FileListAdapter(this)
        binding.fileListRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = fileListAdapter
        }
    }

    override fun onFolderClick(fileObject: FileObject) {
        if (fileObject.isAccessible)
            viewModel.getFilesInPath(fileObject.path)
        else
            Toast.makeText(this, "Can not access this folder.", Toast.LENGTH_SHORT).show()
    }

    override fun onFileClick(fileObject: FileObject) {
    }

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handleBackPressed()
            }
        }

    private fun handleBackPressed() {
        if (!viewModel.onBackPress()) {
            //viewmodel has not handeled back press
            onBackPressedCallback.isEnabled = false
            Toast.makeText(this@MainActivity, "Press again to exit!", Toast.LENGTH_SHORT).show()
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    //backOnce = false
                    onBackPressedCallback.isEnabled = true
                }
            }, 1000)
        }
    }

}