package com.coding.codingapplication.api

import com.coding.codingapplication.model.Album
import com.coding.codingapplication.model.Resource
import retrofit2.http.GET




interface APIService {
    @GET(".")
    suspend fun getAlbums(): List<Album>
}