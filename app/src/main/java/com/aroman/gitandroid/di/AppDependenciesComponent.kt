package com.aroman.gitandroid.di

import com.aroman.gitandroid.ui.userDetails.UserDetailsFragment
import com.aroman.gitandroid.ui.userList.UserListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppDependenciesModule::class
    ]
)
interface AppDependenciesComponent {
    fun injectUserDetailsFragment(userDetailsFragment: UserDetailsFragment)
    fun injectUserListFragment(userListFragment: UserListFragment)
}
