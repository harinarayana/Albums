package com.coding.codingapplication.utils

import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coding.codingapplication.R
import com.coding.codingapplication.ui.AlbumAdapter

@BindingAdapter("adapter")
fun bindRecyclerViewAdapter(view: RecyclerView, albumAdapter: AlbumAdapter) {
    view.apply {
        adapter = albumAdapter
        layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        //ToDO remove this since recyclerview without divider looks better in UI
       /* val dividerItemDecoration = DividerItemDecoration(
            this.getContext(),
            LinearLayoutManager.VERTICAL
        )
        ContextCompat.getDrawable(
            this.context, R.drawable.item_divider
        )?.let {
            dividerItemDecoration.setDrawable(it)
        }
        addItemDecoration(dividerItemDecoration)*/
    }
}