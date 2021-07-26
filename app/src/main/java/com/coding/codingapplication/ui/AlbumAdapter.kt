package com.coding.codingapplication.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.coding.codingapplication.databinding.AlbumItemBinding
import com.coding.codingapplication.model.Album
import dagger.hilt.android.scopes.ActivityRetainedScoped


@ActivityRetainedScoped
class AlbumAdapter: PagingDataAdapter<Album, RecyclerView.ViewHolder>(REPO_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AlbumItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)  =
        (holder as ViewHolder).bind(getItem(position))


    class ViewHolder(val binding: AlbumItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Album?) = with(binding) {
            val anim = AlphaAnimation(0.0f, 1.0f)
            anim.duration = 500
            binding.root.startAnimation(anim)
            binding.album = item
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Album>() {
            override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean =
                oldItem.album_id == newItem.album_id

            override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean =
                oldItem == newItem
        }
    }
}