package com.aroman.gitandroid.ui.userList.recyclerView

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aroman.gitandroid.domain.entities.UserEntity

class UserListAdapter(
    private val itemClickCallback: (UserEntity) -> Unit
) : RecyclerView.Adapter<UserListViewHolder>() {
    var data: List<UserEntity> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserListViewHolder.createView(parent)

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.bind(data[position], itemClickCallback)
    }

    override fun getItemCount(): Int = data.size
}