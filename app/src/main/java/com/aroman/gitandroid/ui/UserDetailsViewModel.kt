package com.aroman.gitandroid.ui

import android.util.Log
import com.aroman.gitandroid.data.GitRetrofitImpl
import com.aroman.gitandroid.domain.entities.GitServerResponseData
import com.aroman.gitandroid.utils.Publisher
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailsViewModel(private val retrofitImpl: GitRetrofitImpl) :
    GitAndroidContract.UserDetailsViewModel {
    override val data: Publisher<GitServerResponseData> = Publisher()

    override fun getUser(login: String) {
        retrofitImpl.getGitRetrofitImpl().listRepos(login).enqueue(
            object : Callback<List<GitServerResponseData>> {
                override fun onResponse(
                    call: Call<List<GitServerResponseData>>,
                    response: Response<List<GitServerResponseData>>
                ) {
                    if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                        for (repo in response.body()!!) {
                            data.post(repo)
                        }
                    }
                }

                override fun onFailure(call: Call<List<GitServerResponseData>>, t: Throwable) {
                    Log.d("@@@", "onFailure: ${t.message}")
                }
            }
        )
    }
}