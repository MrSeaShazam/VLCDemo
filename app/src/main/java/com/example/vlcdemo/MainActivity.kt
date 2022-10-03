package com.example.vlcdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.vlcdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by viewBinding()
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            navController =
                fragmentContainerView.getFragment<NavHostFragment>().navController
        }
    }
}