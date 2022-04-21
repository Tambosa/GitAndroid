package com.aroman.gitandroid.ui.userDetails

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import by.kirich1409.viewbindingdelegate.viewBinding
import com.aroman.gitandroid.app
import com.aroman.gitandroid.databinding.FragmentUserDetailsBinding
import com.aroman.gitandroid.domain.entities.GitServerResponseData
import com.aroman.gitandroid.ui.userDetails.recyclerView.UserDetailsAdapter
import com.aroman.gitandroid.ui.userDetails.recyclerView.UserDetailsDiffUtilCallback
import com.squareup.picasso.Picasso

private const val LOGIN = "login"
private const val REPO_LIST = "repo_list"
private const val AVATAR_URL = "avatar_url"

class UserDetailsFragment : Fragment() {
    private var login = "login"
    private var repoList: ArrayList<GitServerResponseData> = ArrayList()
    private var avatarUrl = ""

    private val binding by viewBinding(FragmentUserDetailsBinding::class.java)
    private lateinit var viewModel: UserDetailsViewModel
    private val handler: Handler by lazy { Handler(Looper.getMainLooper()) }
    private val adapter = UserDetailsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            login = bundle.getString(LOGIN)!!
        }

        viewModel = restoreViewModel()
        avatarUrl = savedInstanceState?.getString(AVATAR_URL).toString()
        val tempList = savedInstanceState?.getParcelableArrayList<GitServerResponseData>(REPO_LIST)
        if (tempList != null) {
            for (item in tempList) {
                repoList.add(item as GitServerResponseData)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(REPO_LIST, repoList)
        outState.putString(AVATAR_URL, repoList[0].owner.avatarUrl)
    }

    private fun restoreViewModel(): UserDetailsViewModel {
        val viewModel =
            requireActivity().lastCustomNonConfigurationInstance as? UserDetailsViewModel
        return viewModel ?: UserDetailsViewModel(
            app.gitRepo,
            app.userLocalRepo
        )
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