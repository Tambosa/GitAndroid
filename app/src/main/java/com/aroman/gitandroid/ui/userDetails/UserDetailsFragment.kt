package com.aroman.gitandroid.ui.userDetails

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.aroman.gitandroid.app
import com.aroman.gitandroid.databinding.FragmentUserDetailsBinding
import com.aroman.gitandroid.domain.entities.GitServerResponseData
import com.aroman.gitandroid.ui.userDetails.recyclerView.UserDetailsAdapter
import com.aroman.gitandroid.ui.userDetails.recyclerView.UserDetailsDiffUtilCallback
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

private const val VIEW_MODEL_ID = "view_model_id"
private const val LOGIN = "login"
private const val REPO_LIST = "repo_list"
private const val AVATAR_URL = "avatar_url"

class UserDetailsFragment : Fragment() {
    private var login = "login"
    private var repoList: ArrayList<GitServerResponseData> = ArrayList()
    private var avatarUrl = ""

    private lateinit var binding: FragmentUserDetailsBinding
    private lateinit var viewModel: UserDetailsViewModel
    private val handler: Handler by lazy { Handler(Looper.getMainLooper()) }
    private val adapter = UserDetailsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            login = bundle.getString(LOGIN)!!
        }

        restoreViewModel(savedInstanceState)

        avatarUrl = savedInstanceState?.getString(AVATAR_URL).toString()
        val tempList = savedInstanceState?.getParcelableArrayList<GitServerResponseData>(REPO_LIST)
        if (tempList != null) {
            for (item in tempList) {
                repoList.add(item as GitServerResponseData)
            }
        }
    }

    private fun restoreViewModel(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            val viewModelId = savedInstanceState.getString(VIEW_MODEL_ID)!!
            viewModel = app.viewModelStore.getViewModel(viewModelId) as UserDetailsViewModel
        } else {
            val id = UUID.randomUUID().toString()
            viewModel = UserDetailsViewModel(app.repoListWeb, app.repoListLocal, id)
            app.viewModelStore.saveViewModel(viewModel)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(REPO_LIST, repoList)
        outState.putString(AVATAR_URL, repoList[0].owner.avatarUrl)
        outState.putString(VIEW_MODEL_ID, viewModel.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginTextView.text = login
        binding.rwUserDetails.adapter = adapter
        viewModel.repos.subscribe(handler) { newRepos ->
            avatarUrl = newRepos!![0].owner.avatarUrl
            initRecyclerView(newRepos)
        }

        if (repoList.isNullOrEmpty()) {
            Log.d("@@@", "Fragment onViewCreated: fetch data from ViewModel")
            viewModel.getUser(isOnline(), login)
        } else {
            Log.d("@@@", "Fragment onViewCreated: restore instance state")
            initRecyclerView(repoList)
        }
    }

    private fun isOnline(): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) != null
    }

    private fun initRecyclerView(repos: List<GitServerResponseData>) {
        Picasso.get().load(avatarUrl).into(binding.avatarImageView)
        repoList.addAll(repos)
        DiffUtil
            .calculateDiff(UserDetailsDiffUtilCallback(adapter.getData(), repoList))
            .dispatchUpdatesTo(adapter)
        adapter.setData(repoList)
    }

    companion object {
        fun newInstance(login: String) =
            UserDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(LOGIN, login)
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.repos.unsubscribeAll()
        viewModel.unSubscribeDisposable()
    }
}