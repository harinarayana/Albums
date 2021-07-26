package com.coding.codingapplication.di

import android.content.Context
import androidx.room.Room
import com.coding.codingapplication.api.APIService
import com.coding.codingapplication.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AlbumModule {
    @Provides
    @Singleton
    fun provideAlbumDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "movie"
        ).allowMainThreadQueries()
            .build()
    @Provides
    fun provideTimeLogAPI(@NetworkModule.BaseRetrofit retrofit: Retrofit): APIService =
        retrofit.create(APIService::class.java)
}