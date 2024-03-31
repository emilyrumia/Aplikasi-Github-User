package com.example.aplikasigithubuser.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasigithubuser.R
import com.example.aplikasigithubuser.data.response.UserResponse
import com.example.aplikasigithubuser.databinding.FavoritesListBinding
import com.example.aplikasigithubuser.ui.main.MainAdapter
import com.example.aplikasigithubuser.ui.setting.SettingActivity
import com.example.aplikasigithubuser.ui.setting.SettingPreferences
import com.example.aplikasigithubuser.ui.setting.SettingViewModel
import com.example.aplikasigithubuser.ui.setting.SettingViewModelFactory
import com.example.aplikasigithubuser.ui.setting.dataStore

class FavoriteActivity : AppCompatActivity()  {
    private lateinit var favoriteBinding: FavoritesListBinding
    private lateinit var favoriteViewModelFactory: FavoriteViewModelFactory
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        favoriteBinding = FavoritesListBinding.inflate(layoutInflater)
        setContentView(favoriteBinding.root)

        favoriteViewModelFactory = FavoriteViewModelFactory.getInstance(applicationContext)
        favoriteViewModel = ViewModelProvider(this, favoriteViewModelFactory).get(FavoriteViewModel::class.java)

        val layoutManager = LinearLayoutManager(this)
        favoriteBinding.rvFavorite.layoutManager = layoutManager

        val adapter = MainAdapter(this)

        favoriteViewModel.getAllFavorites().observe(this){users ->
            val favorites = arrayListOf<UserResponse>()
            users.forEach {
                val user = UserResponse( login = it.username, avatarUrl = it.avatarUrl.orEmpty())
                favorites.add(user)
            }
            adapter.submitList(favorites)
        }


        favoriteBinding.rvFavorite.adapter = adapter

        val settingsButton = findViewById<ImageView>(R.id.ibSetting)
        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingViewModel = ViewModelProvider(this, SettingViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val backButton = findViewById<ImageView>(R.id.ibBack)
        backButton.setOnClickListener { onBackPressed() }

    }
}