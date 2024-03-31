package com.example.aplikasigithubuser.data.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.aplikasigithubuser.BuildConfig

class ApiConfig {

    companion object {
        fun getApiService(): ApiService {
            val apiHttp = if(BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }

            val apiAuth = Interceptor { chain ->
                val req = chain.request()
                val requestHeaders = req.newBuilder()
                    .addHeader("Authorization", BuildConfig.API_KEY)
                    .build()
                chain.proceed(requestHeaders)
            }

            val apiClient = OkHttpClient.Builder()
                .addInterceptor(apiHttp)
                .addInterceptor(apiAuth)
                .build()

            val apiRetrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(apiClient)
                .build()
            return apiRetrofit.create(ApiService::class.java)
        }
    }
}
