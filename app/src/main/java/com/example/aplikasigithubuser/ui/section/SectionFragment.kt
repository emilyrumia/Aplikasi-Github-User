package com.example.aplikasigithubuser.ui.section

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasigithubuser.data.response.UserResponse
import com.example.aplikasigithubuser.databinding.FollowersFollowingBinding
import com.example.aplikasigithubuser.ui.user.UserAdapter

class SectionFragment : Fragment() {
    private lateinit var followBinding: FollowersFollowingBinding
    private lateinit var followViewModel: SectionViewModel

    private var position: Int = 0
    private var username: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        followBinding = FollowersFollowingBinding.inflate(layoutInflater)
        return followBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        followViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(SectionViewModel::class.java)
        followBinding.rvFollow.layoutManager = LinearLayoutManager(requireContext())

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        val username = username ?: return

        followViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        when (position) {
            1 -> {
                followViewModel.userFollowers.observe(viewLifecycleOwner) {
                    setUserData(it)
                }
                followViewModel.findFollowers(username)
            }
            else -> {
                followViewModel.userFollowing.observe(viewLifecycleOwner) {
                    setUserData(it)
                }
                followViewModel.findFollowing(username)
            }
        }
    }

    private fun setUserData(user: List<UserResponse>) {
        val adapter = UserAdapter()
        adapter.submitList(user)
        followBinding.rvFollow.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            followBinding.pbFollow.visibility = View.VISIBLE
        } else {
            followBinding.pbFollow.visibility = View.GONE
        }
    }

    companion object {
        const val ARG_POSITION: String = "position"
        const val ARG_USERNAME: String = "username"
    }
}