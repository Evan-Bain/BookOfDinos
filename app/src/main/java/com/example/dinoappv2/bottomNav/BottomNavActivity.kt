package com.example.dinoappv2.bottomNav

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.dinoappv2.R
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
        //make home icon(middle icon) in bottom nav automatically selected
        binding.bottomNav.selectedItemId = R.id.home_bottom_nav

        //setting up bottom nav with nav graph
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.bottom_nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)

    }

    //make view model visible to the 3 fragments correlated to the bottom navigation view
    companion object {
        lateinit var viewModel: BottomNavViewModel
    }

}

