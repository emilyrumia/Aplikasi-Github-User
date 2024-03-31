package com.example.aplikasigithubuser.ui.section

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasigithubuser.data.response.UserResponse
import com.example.aplikasigithubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SectionViewModel: ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userFollowers = MutableLiveData<List<UserResponse>>()
    val userFollowers: LiveData<List<UserResponse>> = _userFollowers

    private val _userFollowing = MutableLiveData<List<UserResponse>>()
    val userFollowing: LiveData<List<UserResponse>> = _userFollowing

    companion object {
        private const val TAG = "SectionViewModel"
    }

    fun findFollowers(user: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(user)
        client.enqueue(object : Callback<List<UserResponse>> {
            override fun onResponse(
                call: Call<List<UserResponse>>,
                response: Response<List<UserResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        _userFollowers.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                _isLoading.value = true
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun findFollowing(user: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(user)
        client.enqueue(object : Callback<List<UserResponse>> {
            override fun onResponse(
                call: Call<List<UserResponse>>,
                response: Response<List<UserResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        _userFollowing.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                _isLoading.value = true
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}

