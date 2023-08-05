package com.k10.transferfiles.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.k10.transferfiles.R
import com.k10.transferfiles.utils.SortType

class SortingOptionAdapter(
    private val context: Context,
    private val data: ArrayList<SortType>
) : BaseAdapter() {
    override fun getCount(): Int = data.size

    override fun getItem(position: Int): SortType = data[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.item_spinner_text, parent, false)
            vh = ItemHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.label.text = data[position].toString()

        return view

    }

    private class ItemHolder(view: View?) {
        val label: TextView

        init {
            label = view?.findViewById(R.id.sortSpinnerItemText) as TextView
        }
    }
}