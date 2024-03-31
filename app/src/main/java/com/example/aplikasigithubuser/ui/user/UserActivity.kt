package com.example.aplikasigithubuser.ui.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.aplikasigithubuser.R
import com.example.aplikasigithubuser.data.response.DetailUserResponse
import com.example.aplikasigithubuser.data.room.FavoriteEntity
import com.google.android.material.tabs.TabLayoutMediator
import com.example.aplikasigithubuser.databinding.UserDetailBinding
import com.example.aplikasigithubuser.ui.section.SectionPagerAdapter
import com.example.aplikasigithubuser.ui.setting.SettingActivity

class UserActivity : AppCompatActivity() {
    private var name: String? = null
    private var avatarUrl: String? = null
    private lateinit var userBinding: UserDetailBinding
    private lateinit var userViewModelFactory: UserViewModelFactory
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userBinding = UserDetailBinding.inflate(layoutInflater)
        setContentView(userBinding.root)

        userViewModelFactory = UserViewModelFactory.getInstance(applicationContext)
        userViewModel = ViewModelProvider(this, userViewModelFactory).get(UserViewModel::class.java)

        name = intent.getStringExtra(USERNAME)
        userViewModel.getUser(name.toString())

        userViewModel.userDetail.observe(this) { user ->
            if (user != null) {
                showUserData(user)
                avatarUrl = user.avatarUrl
            }
        }

        userViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        userViewModel.getFavorite(name.toString()).observe(this) { favoriteEntities ->
            userBinding.fbFavorite.setOnClickListener {
                val favoriteEntity = FavoriteEntity(
                    username = name.toString(), avatarUrl = avatarUrl.toString())
                if (favoriteEntities.isNullOrEmpty()) {
                    userViewModel.insertFavorite(favoriteEntity)
                    showToast("Added to your favorites")
                } else {
                    userViewModel.deleteFavorite(favoriteEntity)
                    showToast("Deleted from your favorites")
                }
            }
        }

        val settingsButton = findViewById<ImageView>(R.id.ibSetting)
        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

    }

    private fun showUserData(users: DetailUserResponse) {
        userBinding.apply {
            tvName.text = users.name
            tvUsername.text = getString(R.string.format_username, users.login)
            tvFollower.text = getString(R.string.format_followers, users.followers)
            tvFollowing.text = getString(R.string.format_following, users.following)
        }
        Glide.with(this)
            .load(users.avatarUrl)
            .circleCrop()
            .into(userBinding.civDetail)

        val sectionPagerAdapter = SectionPagerAdapter(this)
        sectionPagerAdapter.username = name.toString()
        userBinding.vpDetail.adapter = sectionPagerAdapter
        TabLayoutMediator(userBinding.tlDetail, userBinding.vpDetail) { tab, position ->
            tab.text = resources.getString(TITLE[position])
        }.attach()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        userBinding.pbDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private val TITLE = intArrayOf(
            R.string.followers,
            R.string.following,
        )
        const val USERNAME = ""
    }
}