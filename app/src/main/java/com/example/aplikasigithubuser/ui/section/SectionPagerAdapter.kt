package com.example.aplikasigithubuser.ui.section

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var username: String = ""

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = SectionFragment()
        fragment.arguments = Bundle().apply {
            putInt(SectionFragment.ARG_POSITION, position + 1)
            putString(SectionFragment.ARG_USERNAME, username)
        }
        return fragment
    }
}