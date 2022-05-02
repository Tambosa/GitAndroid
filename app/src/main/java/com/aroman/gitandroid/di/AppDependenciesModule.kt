package com.aroman.gitandroid.di

import android.content.Context
import com.aroman.gitandroid.data.db.room.UserLocalRepo
import com.aroman.gitandroid.data.mock.MockUserListRepoImpl
import com.aroman.gitandroid.data.web.github.GitApi
import com.aroman.gitandroid.data.web.github.GitRepoRetrofitImpl
import com.aroman.gitandroid.domain.usecase.RepositoryUsecase
import com.aroman.gitandroid.domain.usecase.UsersUsecase
import com.aroman.gitandroid.ui.userDetails.UserDetailsViewModel
import com.aroman.gitandroid.ui.userList.UserListViewModel
import dagger.Module
import dagger.Provides
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppDependenciesModule(val context: Context) {

    @Provides
    @Singleton
    fun provideBaseUrl(): String {
        return "https://api.github.com/"
    }

    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(baserUrl: String, factory: Converter.Factory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baserUrl)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(factory)
            .build()
    }

    @Provides
    @Singleton
    fun provideGitApi(retrofit: Retrofit): GitApi {
        return retrofit.create(GitApi::class.java)
    }

    @Named("web")
    @Provides
    @Singleton
    fun provideWebRepositoryUsecase(gitApi: GitApi): RepositoryUsecase {
        return GitRepoRetrofitImpl(gitApi)
    }

    @Named("local")
    @Provides
    @Singleton
    fun provideLocalRepositoryUsecase(): RepositoryUsecase {
        return UserLocalRepo(context)
    }

    @Provides
    @Singleton
    fun provideUsersUsecase(): UsersUsecase {
        return MockUserListRepoImpl()
    }

    @Provides
    @Singleton
    fun provideUserDetailsViewModel(
        @Named("web") web: RepositoryUsecase,
        @Named("local") local: RepositoryUsecase
    ): UserDetailsViewModel {
        return UserDetailsViewModel(web, local)
    }

    @Provides
    @Singleton
    fun provideUserListViewModel(usersUsecase: UsersUsecase): UserListViewModel {
        return UserListViewModel(usersUsecase)
    }
}