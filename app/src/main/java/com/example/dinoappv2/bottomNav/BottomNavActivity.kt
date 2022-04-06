package com.example.dinoappv2.bottomNav

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.dinoappv2.R
import com.example.dinoappv2.companionObjects.CompanionObject
import com.example.dinoappv2.databases.DinosaurEncyclopediaDatabase
import com.example.dinoappv2.databases.ProfileImageDatabase
import com.example.dinoappv2.databinding.ActivityBottomNavBinding
import com.example.dinoappv2.viewModels.BottomNavViewModel
import com.example.dinoappv2.viewModels.EncyclopediaViewModelFactory

class BottomNavActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomNavBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dinoDatasource = DinosaurEncyclopediaDatabase.getInstance(this)
            .dinosaurEncyclopediaDao
        val profileDatasource = ProfileImageDatabase.getInstance(this)
            .profileImageDao
        val viewModelFactory = EncyclopediaViewModelFactory(dinoDatasource, profileDatasource)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(BottomNavViewModel::class.java)

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

    //make view model visible to the 3 fragments correlated to the bottom navigation view
    companion object {
        lateinit var viewModel: BottomNavViewModel
    }

}

