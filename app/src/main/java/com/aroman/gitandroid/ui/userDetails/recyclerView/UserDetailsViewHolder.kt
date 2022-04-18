package com.aroman.gitandroid.ui.userDetails.recyclerView

import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aroman.gitandroid.databinding.ItemUserDetailsBinding
import com.aroman.gitandroid.domain.entities.GitServerResponseData

class UserDetailsViewHolder(private val binding: ItemUserDetailsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup): UserDetailsViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return UserDetailsViewHolder(ItemUserDetailsBinding.inflate(inflater))
        }
    }

    fun bind(item: GitServerResponseData) {
        binding.itemGitRepoName.text = Html.fromHtml(
            "<a href=\"" + item.repoHtmlUrl + "\">" + item.repoName + "</a>",
            Html.FROM_HTML_MODE_LEGACY
        )
        binding.itemGitRepoName.movementMethod = LinkMovementMethod.getInstance()
    }
}
