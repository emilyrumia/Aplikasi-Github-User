package com.example.aplikasigithubuser.ui.user

import android.util.Log
import androidx.lifecycle.*
import com.example.aplikasigithubuser.data.repository.FavoriteRepository
import retrofit2.*
import com.example.aplikasigithubuser.data.response.DetailUserResponse
import com.example.aplikasigithubuser.data.retrofit.ApiConfig
import com.example.aplikasigithubuser.data.room.FavoriteEntity

class UserViewModel(private val favoritesRepository: FavoriteRepository): ViewModel() {

    private val _userDetail = MutableLiveData<DetailUserResponse>()
    val userDetail: LiveData<DetailUserResponse> = _userDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "UserViewModel"
    }

    fun getUser(user: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(user)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        _userDetail.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = true
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getFavorite(username: String) = favoritesRepository.getFavorite(username)

    fun insertFavorite(favoriteEntity: FavoriteEntity) {
        favoritesRepository.insert(
            FavoriteEntity(username = favoriteEntity.username, avatarUrl = favoriteEntity.avatarUrl))
    }

    fun deleteFavorite(favoriteEntity: FavoriteEntity) {
        favoritesRepository.delete(
            FavoriteEntity(username = favoriteEntity.username, avatarUrl = favoriteEntity.avatarUrl))
    }
}