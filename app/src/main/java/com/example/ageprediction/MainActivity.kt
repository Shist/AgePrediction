package com.example.ageprediction

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.ageprediction.databinding.ActivityMainBinding
import com.example.ageprediction.ui.favorites.FavoritesFragment
import com.example.ageprediction.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.activity_main_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)

        navView.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.navigation_home -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.activity_main_fragment_container, HomeFragment())
                        .commit()
                }
                R.id.navigation_favorites -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.activity_main_fragment_container, FavoritesFragment())
                        .commit()
                }
            }
            true
        }

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.activity_main_fragment_container, HomeFragment())
                .commit()
        }
    }
}