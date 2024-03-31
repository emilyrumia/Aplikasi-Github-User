package com.example.aplikasigithubuser.data.repository

import androidx.lifecycle.LiveData
import com.example.aplikasigithubuser.data.room.FavoriteDao
import com.example.aplikasigithubuser.data.room.FavoriteEntity

class FavoriteRepository private constructor(
    private val favoriteDao: FavoriteDao,
    private val appExecutors: AppExecutors,
){

    fun insert(favoriteEntity: FavoriteEntity) {
        appExecutors.diskIO.execute { favoriteDao.insert(favoriteEntity) }
    }

    fun delete(favoriteEntity: FavoriteEntity) {
        appExecutors.diskIO.execute { favoriteDao.delete(favoriteEntity) }
    }

    fun getFavorite(username: String): LiveData<List<FavoriteEntity>> = favoriteDao.getFavorite(username)

    fun getAllFavorites(): LiveData<List<FavoriteEntity>> = favoriteDao.getAllFavorites()

    companion object {
        @Volatile
        private var instance: FavoriteRepository? = null
        fun getInstance(
            favoriteDao: FavoriteDao,
            appExecutors: AppExecutors,
        ): FavoriteRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteRepository(favoriteDao,  appExecutors)
            }.also { instance = it }
    }
}