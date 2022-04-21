package com.example.dinoappv2.bottomNav

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.dinoappv2.R
import com.example.dinoappv2.companionObjects.CompanionObject
import com.example.dinoappv2.databinding.ActivityBottomNavBinding

class BottomNavActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomNavBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setting up bottom nav with nav graph
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.bottom_nav_host) as NavHostFragment
        val navController = navHostFragment.navController

        //if transition to dictionary is true immediately open dictionary fragment upon entering
        //else set the home icon as checked
        if(CompanionObject.transitionToDictionary) {
            navController.navigate(R.id.dictionary_bottom_nav)
            binding.bottomNav.selectedItemId = R.id.dictionary_bottom_nav
            CompanionObject.transitionToDictionary = false
        } else {
            binding.bottomNav.selectedItemId = R.id.home_bottom_nav
        }
        binding.bottomNav.setupWithNavController(navController)
        setSupportActionBar(binding.bottomNavToolbar)
    }

}

