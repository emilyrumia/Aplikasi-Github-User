package com.example.aplikasigithubuser.ui.user

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aplikasigithubuser.data.di.Injection
import com.example.aplikasigithubuser.data.repository.FavoriteRepository

class UserViewModelFactory(private val favoriteRepository: FavoriteRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(favoriteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: UserViewModelFactory? = null
        fun getInstance(context: Context): UserViewModelFactory = instance ?: synchronized(this) {
            instance ?: UserViewModelFactory(Injection.provideRepositoryFavorite(context))
        }.also { instance = it }
    }
}