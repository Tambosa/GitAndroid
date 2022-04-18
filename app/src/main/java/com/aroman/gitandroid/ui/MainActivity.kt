package com.aroman.gitandroid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.aroman.gitandroid.databinding.ActivityMainBinding
import com.aroman.gitandroid.ui.userDetails.UserDetailsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initHardcodedUserList()
    }

    private fun initHardcodedUserList() {
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
            )
        )
        params.weight = 1f
        val user1 = TextView(this).apply {
            text = "tambosa"
            textSize = 40f
            gravity = Gravity.CENTER
            layoutParams = params
        }
        val user2 = TextView(this).apply {
            text = "borhammere"
            textSize = 40f
            gravity = Gravity.CENTER
            layoutParams = params
        }
        val user3 = TextView(this).apply {
            text = "JakeWharton"
            textSize = 40f
            gravity = Gravity.CENTER
            layoutParams = params
        }
        val user4 = TextView(this).apply {
            text = "fabpot"
            textSize = 40f
            gravity = Gravity.CENTER
            layoutParams = params
        }

        binding.llMainLayout.addView(user1)
        binding.llMainLayout.addView(user2)
        binding.llMainLayout.addView(user3)
        binding.llMainLayout.addView(user4)

        user1.setOnClickListener {
            initUserDetailsFragment(user1.text.toString())
        }
        user2.setOnClickListener {
            initUserDetailsFragment(user2.text.toString())
        }
        user3.setOnClickListener {
            initUserDetailsFragment(user3.text.toString())
        }
        user4.setOnClickListener {
            initUserDetailsFragment(user4.text.toString())
        }

    }

    private fun initUserDetailsFragment(userName: String) {
        Log.d("@@@", "initUserDetailsFragment: $userName")
        supportFragmentManager.beginTransaction()
            .add(binding.llMainLayout.id, UserDetailsFragment.newInstance(userName))
            .addToBackStack("")
            .commit()
    }
}