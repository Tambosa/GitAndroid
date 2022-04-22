package com.aroman.gitandroid.ui.userDetails.recyclerView

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aroman.gitandroid.data.web.github.GitServerResponseData

class UserDetailsAdapter : RecyclerView.Adapter<UserDetailsViewHolder>() {
    private var data: List<GitServerResponseData> = emptyList()

    fun setData(repos: List<GitServerResponseData>) {
        data = repos
    }

    fun getData() = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDetailsViewHolder =
        UserDetailsViewHolder.create(parent)

    override fun onBindViewHolder(holder: UserDetailsViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}
