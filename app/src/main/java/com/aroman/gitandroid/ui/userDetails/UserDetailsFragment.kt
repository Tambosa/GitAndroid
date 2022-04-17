package com.aroman.gitandroid.ui.userDetails

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.aroman.gitandroid.app
import com.aroman.gitandroid.databinding.FragmentUserDetailsBinding
import com.aroman.gitandroid.domain.entities.GitServerResponseData
import com.aroman.gitandroid.ui.userDetails.recyclerView.UserDetailsAdapter
import com.aroman.gitandroid.ui.userDetails.recyclerView.UserDetailsDiffUtilCallback
import com.squareup.picasso.Picasso

private const val LOGIN = "login"

class UserDetailsFragment : Fragment() {
    private var login = "login"
    private val repoList: MutableList<GitServerResponseData> = ArrayList()

    private lateinit var binding: FragmentUserDetailsBinding
    private lateinit var viewModel: UserDetailsViewModel
    private val handler: Handler by lazy { Handler(Looper.getMainLooper()) }
    private val adapter = UserDetailsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            login = it.getString(LOGIN)!!
        }
        viewModel = restoreViewModel()
    }

    private fun restoreViewModel(): UserDetailsViewModel {
        val viewModel =
            requireActivity().lastCustomNonConfigurationInstance as? UserDetailsViewModel
        return viewModel ?: UserDetailsViewModel(
            requireActivity().app.gitRepo,
            requireActivity().app.userLocalRepo
        )
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
        repoList.clear()
        viewModel.repos.subscribe(handler) { newRepos ->
            Picasso.get().load(newRepos!![0].owner.avatarUrl).into(binding.avatarImageView)
            repoList.addAll(newRepos)
            DiffUtil
                .calculateDiff(UserDetailsDiffUtilCallback(adapter.getData(), repoList))
                .dispatchUpdatesTo(adapter)
            adapter.setData(repoList)
        }
        viewModel.getUser(login)
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
        repoList.clear()
    }
}