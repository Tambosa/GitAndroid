package com.aroman.gitandroid.di

import com.aroman.gitandroid.data.db.room.UserLocalRepo
import com.aroman.gitandroid.data.mock.MockUserListRepoImpl
import com.aroman.gitandroid.data.web.github.GitApi
import com.aroman.gitandroid.data.web.github.GitRepoRetrofitImpl
import com.aroman.gitandroid.domain.usecase.RepositoryUsecase
import com.aroman.gitandroid.domain.usecase.UsersUsecase
import com.aroman.gitandroid.ui.userDetails.UserDetailsViewModel
import com.aroman.gitandroid.ui.userList.UserListViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single<String>(named("api_url")) { "https://api.github.com/" }

    single<RepositoryUsecase>(named("web")) { GitRepoRetrofitImpl() }
    single<GitApi> { get<Retrofit>().create(GitApi::class.java) }
    single {
        Retrofit.Builder()
            .baseUrl(get<String>(named("api_url")))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<RepositoryUsecase>(named("local")) { UserLocalRepo(get()) }
    single<UsersUsecase> { MockUserListRepoImpl() }

    single<UserDetailsViewModel> { UserDetailsViewModel(get(named("web")), get(named("local"))) }
    single<UserListViewModel> { UserListViewModel(get()) }
}