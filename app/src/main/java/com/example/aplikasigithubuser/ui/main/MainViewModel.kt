package com.example.aplikasigithubuser.ui.main

import androidx.lifecycle.*
import retrofit2.*
import android.util.Log
import com.example.aplikasigithubuser.data.response.GithubResponse
import com.example.aplikasigithubuser.data.response.UserResponse
import com.example.aplikasigithubuser.data.retrofit.ApiConfig

class MainViewModel : ViewModel() {

    private val _users = MutableLiveData<List<UserResponse>>()
    val users: LiveData<List<UserResponse>> = _users

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "MainViewModel"
        private const val USER = "Emily"
    }

    init {
        findUser(USER)
    }

    fun findUser(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers(query)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _users.value = response.body()?.items as List<UserResponse>
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}