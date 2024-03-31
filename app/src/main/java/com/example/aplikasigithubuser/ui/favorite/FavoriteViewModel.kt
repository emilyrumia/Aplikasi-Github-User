package com.example.aplikasigithubuser.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasigithubuser.data.repository.FavoriteRepository
import com.example.aplikasigithubuser.data.room.FavoriteEntity

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {

    fun getAllFavorites(): LiveData<List<FavoriteEntity>> {
        return favoriteRepository.getAllFavorites()
    }

}