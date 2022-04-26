package com.aroman.gitandroid.ui.userList

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.aroman.gitandroid.R
import com.aroman.gitandroid.domain.entity.UserEntity
import com.aroman.gitandroid.databinding.FragmentUserListBinding
import com.aroman.gitandroid.domain.FragmentController
import com.aroman.gitandroid.ui.userList.recyclerView.UserListAdapter
import org.koin.android.ext.android.inject

class UserListFragment : Fragment(R.layout.fragment_user_list) {
    private val binding by viewBinding(FragmentUserListBinding::class.java)
    private val handler: Handler by lazy { Handler(Looper.getMainLooper()) }
    private val viewModel: UserListViewModel by inject()
    private val controller by lazy { activity as FragmentController }
    private val userListAdapter = UserListAdapter {
        controller.openUserDetails(it.userName)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity !is FragmentController) {
            throw IllegalStateException("Activity должна наследоваться от FragmentController")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.userList.subscribe(handler) { userList ->
            if (userList != null) {
                initRecyclerView(userList)
            }
        }
        viewModel.getUserList()
    }

    private fun initRecyclerView(userList: List<UserEntity>) {
        binding.userListRecyclerView.adapter = userListAdapter
        userListAdapter.data = userList
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.userList.unsubscribeAll()
    }
}