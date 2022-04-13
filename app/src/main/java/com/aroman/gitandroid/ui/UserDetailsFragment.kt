package com.aroman.gitandroid.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.aroman.gitandroid.data.GitRetrofitImpl
import com.aroman.gitandroid.databinding.FragmentUserDetailsBinding
import com.aroman.gitandroid.domain.entities.GitServerResponseData
import com.squareup.picasso.Picasso

private const val LOGIN = "login"

class UserDetailsFragment() : Fragment() {
    private var login = "login"
    private lateinit var binding: FragmentUserDetailsBinding
    private val viewModel: UserDetailsViewModel by lazy { UserDetailsViewModel(GitRetrofitImpl()) }
    private val handler: Handler by lazy { Handler(Looper.getMainLooper()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            login = it.getString(LOGIN)!!
        }
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

        viewModel.data.subscribe(handler) { repo ->
            Picasso.get().load(repo?.owner?.avatarUrl).into(binding.avatarImageView)
            initRepoList(repo)
        }
        viewModel.getUser(login)
    }

    private fun initRepoList(repo: GitServerResponseData?) {
        binding.llRepoList.addView(
            TextView(requireActivity()).apply {
                text = Html.fromHtml(
                    "<a href=\"" + repo?.repoHtmlUrl + "\">" + repo?.repoName + "</a>",
                    Html.FROM_HTML_MODE_LEGACY
                )
                textSize = 30f
                gravity = Gravity.CENTER
                movementMethod = LinkMovementMethod.getInstance()
            }
        )
    }

    companion object {
        fun newInstance(login: String) =
            UserDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(LOGIN, login)
                }
            }
    }
}