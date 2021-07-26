package com.coding.codingapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import com.coding.codingapplication.R
import com.coding.codingapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )
        val adapter = AlbumAdapter()
        binding.viewModel = mainViewModel
        binding.adapter = adapter

        lifecycleScope.launch {
           mainViewModel.fetchAlbumData().distinctUntilChanged().collectLatest {
                adapter.submitData(it)
            }
            mainViewModel.loadAlbumData()
        }
    }
}