package com.coding.codingapplication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.coding.codingapplication.data.Repository
import com.coding.codingapplication.model.Album
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
public class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel(){

    val albumData : LiveData<List<Album>>
    get() = _albumData
    private val _albumData: MutableLiveData<List<Album>> = MutableLiveData()

    //Not using for now.
    suspend fun loadAlbumData(){
        _albumData.postValue(repository.getAllAlbums())
    }

    /**
     * Method to load paged data from Page Source and API.
     * */
    fun fetchAlbumData(): Flow<PagingData<Album>> {
        return repository.letAlbumsDataFlowDb().cachedIn(viewModelScope)
    }
}