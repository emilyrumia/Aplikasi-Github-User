package com.example.aplikasigithubuser.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aplikasigithubuser.data.response.UserResponse
import com.example.aplikasigithubuser.databinding.ItemUserBinding

class UserAdapter : ListAdapter<UserResponse, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(private val itemBinding: ItemUserBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(user: UserResponse) {
            fun ImageView.loadUserAvatar(url: String) {
                Glide.with(this)
                    .load(url)
                    .centerCrop()
                    .into(this)
            }

            itemBinding.apply {
                tvItem.text = user.login
                user.avatarUrl?.let {
                    civItem.loadUserAvatar(it)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemBinding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserResponse>() {
            override fun areItemsTheSame( oldItem: UserResponse, newItem: UserResponse)
            : Boolean { return oldItem == newItem }

            override fun areContentsTheSame(oldItem: UserResponse, newItem: UserResponse)
            : Boolean { return oldItem == newItem }
        }
    }
}
