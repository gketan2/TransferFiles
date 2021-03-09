package com.k10.transferfiles.learning

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.k10.transferfiles.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity(), FilesAdapter.OnFileClickListener {

    private lateinit var filesAdapter: FilesAdapter

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        filesAdapter = FilesAdapter(this)
        binding.filesRecyclerView.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = filesAdapter
        }
    }

    override fun onFileClicked(position: Int, item: File) {
        Toast.makeText(this, "${item.fileName}:${item.filePath}", Toast.LENGTH_SHORT).show()
    }
}