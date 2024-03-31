package com.example.aplikasigithubuser.ui.main

import androidx.appcompat.app.*
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasigithubuser.R
import com.example.aplikasigithubuser.data.response.UserResponse
import com.example.aplikasigithubuser.databinding.ActivityMainBinding
import com.example.aplikasigithubuser.ui.favorite.FavoriteActivity
import com.example.aplikasigithubuser.ui.setting.SettingActivity
import com.example.aplikasigithubuser.ui.setting.SettingPreferences
import com.example.aplikasigithubuser.ui.setting.SettingViewModel
import com.example.aplikasigithubuser.ui.setting.SettingViewModelFactory
import com.example.aplikasigithubuser.ui.setting.dataStore

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        with(mainBinding) {
            svUser.setupWithSearchBar(sbUser)
            svUser
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    sbUser.setText(svUser.text)
                    mainViewModel.findUser(svUser.text.toString())
                    svUser.hide()
                    showLoading(true)
                    false
            }
        }

        val layoutManager = LinearLayoutManager(this)
        mainBinding.rvUser.layoutManager = layoutManager

        mainViewModel.users.observe(this) { user ->
            showUser(user)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val favoriteButton = findViewById<ImageView>(R.id.ibFavorite)
        favoriteButton.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }

        val settingsButton = findViewById<ImageView>(R.id.ibSetting)
        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingViewModel = ViewModelProvider(this, SettingViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

    }

    private fun showUser(user: List<UserResponse>?) {
        val adapter = MainAdapter(this)
        adapter.submitList(user)
        mainBinding.rvUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        mainBinding.pbUser.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}