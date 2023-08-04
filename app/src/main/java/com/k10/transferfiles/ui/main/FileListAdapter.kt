package com.k10.transferfiles.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.k10.transferfiles.databinding.ListFileBinding
import com.k10.transferfiles.models.FileObject
import com.k10.transferfiles.utils.Extensions.getIcon

class FileListAdapter(private val communicator: FileListCommunicator) :
    RecyclerView.Adapter<FileListViewHolder>() {

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FileObject>() {

        override fun areItemsTheSame(oldItem: FileObject, newItem: FileObject): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: FileObject, newItem: FileObject): Boolean {
            return oldItem.name == newItem.name
                    && oldItem.fileType == newItem.fileType
                    && oldItem.path == newItem.path
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileListViewHolder {
        return FileListViewHolder(
            ListFileBinding.inflate(LayoutInflater.from(parent.context)),
            communicator
        )
    }

    override fun onBindViewHolder(holder: FileListViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(data: List<FileObject>) {
        differ.submitList(data)
    }
}

interface FileListCommunicator {
    fun onFolderClick(fileObject: FileObject)
    fun onFileClick(fileObject: FileObject)
}

class FileListViewHolder(
    private val binding: ListFileBinding,
    private val communicator: FileListCommunicator
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(fileObject: FileObject) {
        binding.fileName.text = fileObject.name
        binding.fileSize.text = fileObject.formattedSize
        Glide.with(binding.root.context)
            .load(fileObject.getIcon(binding.root.context.packageName))
            .into(binding.fileIcon)
        binding.root.setOnClickListener {
            if (fileObject.isFolder)
                communicator.onFolderClick(fileObject)
            else
                communicator.onFileClick(fileObject)
        }
    }
}