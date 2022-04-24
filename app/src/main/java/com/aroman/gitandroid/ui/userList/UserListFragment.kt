package com.aroman.gitandroid.ui.userList

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.aroman.gitandroid.R
import com.aroman.gitandroid.app
import com.aroman.gitandroid.databinding.FragmentUserListBinding
import com.aroman.gitandroid.domain.FragmentController
import com.aroman.gitandroid.data.mock.UserEntity
import com.aroman.gitandroid.ui.userList.recyclerView.UserListAdapter
import java.lang.IllegalStateException
import java.util.*

private const val VIEW_MODEL_ID = "view_model_id"

class UserListFragment : Fragment(R.layout.fragment_user_list) {
    private val binding by viewBinding(FragmentUserListBinding::class.java)
    private val handler: Handler by lazy { Handler(Looper.getMainLooper()) }

    private lateinit var viewModel: UserListViewModel
    private val controller by lazy { activity as FragmentController }
    private val userListAdapter = UserListAdapter {
        controller.openUserDetails(it.userName)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restoreViewModel(savedInstanceState)
    }

    private fun restoreViewModel(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            val viewModelId = savedInstanceState.getString(VIEW_MODEL_ID)!!
            viewModel = app.viewModelStore.getViewModel(viewModelId) as UserListViewModel
        } else {
            val id = UUID.randomUUID().toString()
            viewModel = UserListViewModel(app.userListRepo, id)
            app.viewModelStore.saveViewModel(viewModel)
        }
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
        binding.rwUserList.adapter = userListAdapter
        userListAdapter.data = userList
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(VIEW_MODEL_ID, viewModel.id)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.userList.unsubscribeAll()
    }
}