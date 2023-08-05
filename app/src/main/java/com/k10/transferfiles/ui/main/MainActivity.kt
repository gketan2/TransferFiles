package com.k10.transferfiles.ui.main

import android.graphics.Typeface.BOLD
import android.os.Bundle
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexDirection
import com.k10.transferfiles.R
import com.k10.transferfiles.databinding.ActivityMainBinding
import com.k10.transferfiles.models.FileObject
import com.k10.transferfiles.ui.BaseActivity
import com.k10.transferfiles.utils.ResultStatus
import com.k10.transferfiles.utils.SortType
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

        binding.pathFlexBox.flexDirection = FlexDirection.ROW
        setUpRecycler()
        viewModel.getFilesInPath()
        viewModel.fileListLiveData.observe(this) {
            when (it.status) {
                ResultStatus.LOADING -> {
                    //binding.progressBar.visible = true
                }

                ResultStatus.SUCCESS -> {
                    //binding.progressBar.visible = false
                    binding.pathFlexBox.removeAllViews()
                    fileListAdapter.submitList(it.data?.files!!)
                    setupPathBreakUpView(it.data.pathList)
                    if (it.data.files.size == 1) {
                        binding.itemCountText.text =
                            getString(R.string.item_in_total, it.data.files.size)
                    } else {
                        binding.itemCountText.text =
                            getString(R.string.items_in_total, it.data.files.size)
                    }
                }

                ResultStatus.FAILED -> {
                    //binding.progressBar.visible = false
                }
            }
        }

        SortingOptionAdapter(this, SortType.getSortOptions()).apply {
            binding.sortSpinner.adapter = this
        }
        binding.sortSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.setSortingType(SortType.getSortOptions()[position])
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        binding.sortSpinner.setSelection(viewModel.getSortingType().type)

        onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_config_select, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val hiddenItem = menu?.findItem(R.id.hiddenFiles)
        hiddenItem?.isChecked = viewModel.getShowHiddenFiles()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.hiddenFiles -> {
                val prev = item.isChecked
                viewModel.setShowHiddenFiles(!prev)
                item.isChecked = !prev
                true
            }

            else -> false
        }
    }

    private fun setUpRecycler() {
        fileListAdapter = FileListAdapter(this)
        binding.fileListRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = fileListAdapter
        }
    }

    private fun setupPathBreakUpView(pathBreakUpList: List<String>) {
        binding.pathFlexBox.removeAllViews()
        pathBreakUpList.forEachIndexed { index, string ->
            if (index == 0) {
                //add root
                val tv = TextView(this).apply {
                    text = "Device Storage"
                    textSize = 12f
                    setTypeface(typeface, BOLD)
                    if (index == pathBreakUpList.size - 1) {
                        val typedValue = TypedValue()
                        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)
                        @ColorInt val color: Int = typedValue.data
                        setTextColor(color)
                    }
                    setOnClickListener {
                        openFileFromList(pathBreakUpList, index)
                    }
                }
                binding.pathFlexBox.addView(tv)
            } else {
                //add arrow, textview
                val tvPartition = TextView(this).apply {
                    text = " > "
                    textSize = 12f
                    setTypeface(typeface, BOLD)
                }
                val tv = TextView(this).apply {
                    text = string
                    textSize = 12f
                    setTypeface(typeface, BOLD)
                    if (index == pathBreakUpList.size - 1) {
                        val typedValue = TypedValue()
                        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)
                        @ColorInt val color: Int = typedValue.data
                        setTextColor(color)
                    }
                    setOnClickListener {
                        openFileFromList(pathBreakUpList, index)
                    }
                }
                binding.pathFlexBox.addView(tvPartition)
                binding.pathFlexBox.addView(tv)
            }
        }
    }

    private fun openFileFromList(pathBreakupList: List<String>, position: Int) {
        // make path
        var path = viewModel.rootPath
        for (i in 1..position) {
            path += "/${pathBreakupList[i]}"
        }
        viewModel.getFilesInPath(path)
    }

    override fun onFolderClick(fileObject: FileObject) {
        if (fileObject.isAccessible)
            viewModel.getFilesInPath(fileObject.path)
        else
            Toast.makeText(this, "Can not access ${fileObject.name}", Toast.LENGTH_SHORT).show()
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