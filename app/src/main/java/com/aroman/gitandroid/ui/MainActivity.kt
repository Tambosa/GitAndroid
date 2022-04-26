package com.aroman.gitandroid.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.aroman.gitandroid.databinding.ActivityMainBinding
import com.aroman.gitandroid.domain.FragmentController
import com.aroman.gitandroid.ui.userDetails.UserDetailsFragment
import com.aroman.gitandroid.ui.userList.UserListFragment

class MainActivity : AppCompatActivity(), FragmentController {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            val userListFragment: Fragment = UserListFragment()
            supportFragmentManager
                .beginTransaction()
                .add(binding.mainContainer.id, userListFragment)
                .commit()
        }
    }

    override fun openUserDetails(userName: String) {
        Log.d("@@@", "initUserDetailsFragment: $userName")
        supportFragmentManager.beginTransaction()
            .replace(binding.mainContainer.id, UserDetailsFragment.newInstance(userName))
            .addToBackStack("")
            .commit()
    }
}