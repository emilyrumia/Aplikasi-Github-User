package com.example.aplikasigithubuser.data.room

import androidx.room.*
import androidx.lifecycle.LiveData

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUserEntity: FavoriteEntity)

    @Delete
    fun delete(favoriteUserEntity: FavoriteEntity)

    @Query("SELECT * FROM favorite")
    fun getAllFavorites(): LiveData<List<FavoriteEntity>>

    @Query("SELECT * FROM favorite WHERE username = :username")
    fun getFavorite(username: String): LiveData<List<FavoriteEntity>>
}