package com.example.aplikasigithubuser.ui.main

import android.view.*
import androidx.recyclerview.widget.*
import android.app.Activity
import android.content.Intent
import com.bumptech.glide.Glide
import com.example.aplikasigithubuser.data.response.UserResponse
import com.example.aplikasigithubuser.databinding.ItemUserBinding
import com.example.aplikasigithubuser.ui.user.UserActivity

class MainAdapter(private val activity: Activity) : ListAdapter<UserResponse, MainAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val mainBinding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(mainBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val items = getItem(position)
        holder.bind(items)
        holder.itemView.setOnClickListener {
            val moveDataUserIntent = Intent(holder.itemView.context, UserActivity::class.java)
            moveDataUserIntent.putExtra(UserActivity.USERNAME, items.login)
            holder.itemView.context.startActivity(moveDataUserIntent)
        }
    }

    class MyViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(items: UserResponse) {
            Glide.with(binding.root)
                .load(items.avatarUrl)
                .into(binding.civItem)

            binding.tvItem.text = items.login
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserResponse>() {
            override fun areItemsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
                return oldItem == newItem
            }
        }
    }
}