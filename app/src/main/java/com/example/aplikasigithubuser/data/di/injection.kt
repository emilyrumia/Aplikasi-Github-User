package com.example.aplikasigithubuser.data.di

import android.content.Context
import com.example.aplikasigithubuser.data.repository.AppExecutors
import com.example.aplikasigithubuser.data.repository.FavoriteRepository
import com.example.aplikasigithubuser.data.room.FavoriteDatabase

object Injection {
    fun provideRepositoryFavorite(context:Context):FavoriteRepository{
        val favoriteDatabase = FavoriteDatabase.getDatabase(context)
        val favoriteDao = favoriteDatabase.favoriteDao()
        val appExecutors = AppExecutors()
        return FavoriteRepository.getInstance(favoriteDao, appExecutors)
    }
}
