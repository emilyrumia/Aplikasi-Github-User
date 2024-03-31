package com.example.aplikasigithubuser.ui.setting

import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.aplikasigithubuser.R
import com.google.android.material.switchmaterial.SwitchMaterial
import com.example.aplikasigithubuser.databinding.SettingModeBinding

class SettingActivity : AppCompatActivity()  {
    private lateinit var settingBinding: SettingModeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingBinding = SettingModeBinding.inflate(layoutInflater)
        setContentView(settingBinding.root)

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingViewModel = ViewModelProvider(this, SettingViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
        }
    }
}