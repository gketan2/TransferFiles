package com.k10.transferfiles.learning

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.k10.transferfiles.databinding.ListFilesBinding

class FilesAdapter(private val interaction: OnFileClickListener? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<File>() {

        override fun areItemsTheSame(oldItem: File, newItem: File): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: File, newItem: File): Boolean {
            return oldItem.fileName == newItem.fileName && oldItem.filePath == newItem.filePath
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return FilesViewModel(
            ListFilesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FilesViewModel -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<File>) {
        differ.submitList(list)
    }

    class FilesViewModel
    constructor(
        private val binding: ListFilesBinding,
        private val interaction: OnFileClickListener?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: File) {
            binding.fileName.text = item.fileName
            binding.filePath.text = item.filePath
            itemView.setOnClickListener {
                interaction?.onFileClicked(adapterPosition, item)
            }
        }
    }

    interface OnFileClickListener {
        fun onFileClicked(position: Int, item: File)
    }
}
