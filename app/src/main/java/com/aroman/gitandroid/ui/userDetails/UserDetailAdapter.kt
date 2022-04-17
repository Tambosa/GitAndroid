package com.aroman.gitandroid.ui.userDetails

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aroman.gitandroid.domain.entities.GitServerResponseData

class UserDetailAdapter : RecyclerView.Adapter<UserDetailsViewHolder>() {
    private var data: List<GitServerResponseData> = emptyList()

    fun setData(repos: List<GitServerResponseData>) {
        data = repos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDetailsViewHolder =
        UserDetailsViewHolder.create(parent)

    override fun onBindViewHolder(holder: UserDetailsViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

}
