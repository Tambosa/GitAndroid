package com.aroman.gitandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.aroman.gitandroid.data.GitRetrofitImpl
import com.aroman.gitandroid.databinding.ActivityMainBinding
import com.aroman.gitandroid.domain.entities.GitServerResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        retrofitImpl.getGitRetrofitImpl().listRepos("tambosa").enqueue(
            object : Callback<List<GitServerResponseData>> {
                override fun onResponse(
                    call: Call<List<GitServerResponseData>>,
                    response: Response<List<GitServerResponseData>>
                ) {
                    Log.d("@@@", "onResponse: ${response.body()}")
                }

                override fun onFailure(call: Call<List<GitServerResponseData>>, t: Throwable) {
                    Log.d("@@@", "onFailure: ${t.message}")
                }
            }

        )
    }

    private val retrofitImpl: GitRetrofitImpl = GitRetrofitImpl()
}