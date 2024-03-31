package com.example.aplikasigithubuser.ui.favorite

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aplikasigithubuser.data.di.Injection
import com.example.aplikasigithubuser.data.repository.FavoriteRepository

class FavoriteViewModelFactory (private val favoriteRepository: FavoriteRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(favoriteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: FavoriteViewModelFactory? = null
        fun getInstance(context: Context): FavoriteViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: FavoriteViewModelFactory(Injection.provideRepositoryFavorite(context))
            }.also { instance = it }
    }
}