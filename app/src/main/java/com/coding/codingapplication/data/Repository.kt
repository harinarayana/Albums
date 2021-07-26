package com.coding.codingapplication.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.coding.codingapplication.api.APIService
import com.coding.codingapplication.db.AppDatabase
import com.coding.codingapplication.db.AppDatabase.Companion.DEFAULT_PAGE_SIZE
import com.coding.codingapplication.model.Album
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiService: APIService,
    private val appDatabase: AppDatabase
) {

    /**
     * let's define page size, page size is the only required param, rest is optional
     */
    fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true)
    }

    @ExperimentalPagingApi
    fun letAlbumsDataFlowDb(
        pagingConfig: PagingConfig = getDefaultPageConfig()
    ): Flow<PagingData<Album>> {
        if (appDatabase == null) throw IllegalStateException("Database is not initialized")

        val pagingSourceFactory = { appDatabase.getAlbumDao().getAllAlbums() }
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = PageKeyedRemoteMediator(apiService, appDatabase)
        ).flow
    }

    suspend fun getAllAlbums() = apiService.getAlbums()
}