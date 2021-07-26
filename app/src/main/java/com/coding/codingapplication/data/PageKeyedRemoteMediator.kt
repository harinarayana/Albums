package com.coding.codingapplication.data

import android.util.Log
import androidx.paging.*
import androidx.room.withTransaction
import com.coding.codingapplication.api.APIService
import com.coding.codingapplication.db.AppDatabase
import com.coding.codingapplication.db.AppDatabase.Companion.DEFAULT_PAGE_INDEX
import com.coding.codingapplication.model.Album
import com.coding.codingapplication.model.RemoteKeys
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PageKeyedRemoteMediator @Inject constructor(
    private val apiService: APIService,
    val appDatabase: AppDatabase
    ) : RemoteMediator<Int, Album>() {

    override suspend fun initialize(): RemoteMediator.InitializeAction {
        // Require that remote REFRESH is launched on initial load and succeeds before launching
        // remote PREPEND / APPEND.
        return RemoteMediator.InitializeAction.LAUNCH_INITIAL_REFRESH
    }


    override suspend fun load(loadType: LoadType, state: PagingState<Int, Album>): MediatorResult {
        val pageKeyData = getKeyPageData(loadType, state)
        val page = when (pageKeyData) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as? Int
            }
        }

        try {
            Log.i("*************", "response**************try")
            val response = apiService.getAlbums().sortedByDescending { it.title }
            Log.i("*************", "response************** $response")
            response.forEach {
                Log.i("*************", "album_id************** ${it.album_id}")
                Log.i("*************", "title************** ${it.title}")
                Log.i("*************", "userId************** ${it.userId}")
            }
            val isEndOfList = response.isEmpty()
            Log.i("*************", "isEndOfList************** $isEndOfList")
            appDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    appDatabase.getRepoDao().clearRemoteKeys()
                    appDatabase.getAlbumDao().clearAll()
                }
                val prevKey = if (page == DEFAULT_PAGE_INDEX) null else page?.minus(1)
                val nextKey = if (isEndOfList == true) null else page?.plus(1)
                val keys = response.map {
                    RemoteKeys(repoId = it.album_id, prevKey = prevKey, nextKey = nextKey)
                }
                appDatabase.getRepoDao().insertAll(keys)
                appDatabase.getAlbumDao().insertAll(response)
            }
            Log.i("*************", "************** before success")
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (exception: IOException) {
            Log.i("*************", "IO************** ${exception.message}")
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            Log.i("*************", "Http************** ${exception.message}")
            return MediatorResult.Error(exception)
        } catch (exception: Exception) {
            Log.i("*************", "Generic************** ${exception.message}")
            return MediatorResult.Error(exception)
        } finally {
            Log.i("*************", "************** finally called")
        }
    }

    /**
     * this returns the page key or the final end of list success result
     */
    suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, Album>): Any? {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getClosestRemoteKey(state)
                remoteKeys?.nextKey?.minus(1) ?: DEFAULT_PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                /*?: throw InvalidObjectException("Remote key should not be null for $loadType")*/
                remoteKeys?.nextKey
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                   /* ?: throw InvalidObjectException("Remote key and the prevKey should not be null")*/
                remoteKeys?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                remoteKeys?.prevKey
            }
        }
    }


    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Album>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { album ->
                appDatabase.getRepoDao().remoteKeysAlbumId(album.album_id)
            }
    }

    /**
     * get the last remote key inserted which had the data
     */
    private suspend fun getLastRemoteKey(state: PagingState<Int, Album>): RemoteKeys? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { album -> appDatabase.getRepoDao().remoteKeysAlbumId(album.album_id) }
    }

    /**
     * get the first remote key inserted which had the data
     */
    private suspend fun getFirstRemoteKey(state: PagingState<Int, Album>): RemoteKeys? {
        return state.pages
            .firstOrNull() { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { album -> appDatabase.getRepoDao().remoteKeysAlbumId(album.album_id) }
    }

    /**
     * get the closest remote key inserted which had the data
     */
    private suspend fun getClosestRemoteKey(state: PagingState<Int, Album>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.album_id?.let { repoId ->
                appDatabase.getRepoDao().remoteKeysAlbumId(repoId)
            }
        }
    }
}