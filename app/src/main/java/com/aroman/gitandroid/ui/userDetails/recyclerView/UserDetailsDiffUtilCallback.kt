package com.aroman.gitandroid.ui.userDetails.recyclerView

import androidx.recyclerview.widget.DiffUtil
import com.aroman.gitandroid.domain.entities.GitServerResponseData

class UserDetailsDiffUtilCallback(
    var oldList: List<GitServerResponseData>,
    var newList: List<GitServerResponseData>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].repoName == newList[newItemPosition].repoName
    }

}