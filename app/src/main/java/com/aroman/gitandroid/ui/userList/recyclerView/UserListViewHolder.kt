package com.aroman.gitandroid.ui.userList.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aroman.gitandroid.R
import com.aroman.gitandroid.databinding.ItemUserBinding
import com.aroman.gitandroid.domain.entities.UserEntity

class UserListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemUserBinding.bind(itemView)

    companion object {
        fun createView(parent: ViewGroup): UserListViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
            return UserListViewHolder(view)
        }
    }

    fun bind(item: UserEntity, listener: (UserEntity) -> Unit) {
        binding.itemUserNameTextView.text = item.userName
        binding.root.setOnClickListener { listener.invoke(item) }
    }
}