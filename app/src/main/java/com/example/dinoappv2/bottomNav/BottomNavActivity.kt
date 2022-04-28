package com.example.dinoappv2.bottomNav

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.dinoappv2.R
import com.example.dinoappv2.companionObjects.CompanionObject
import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia
import com.example.dinoappv2.databinding.ActivityBottomNavBinding
import com.google.android.material.appbar.AppBarLayout

class BottomNavActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomNavBinding
    private lateinit var navController: NavController
    private var originalFragmentPadding = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activity = this
        originalFragmentPadding = binding.bottomNavHost.paddingBottom

        //setting up bottom nav with nav graph
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.bottom_nav_host) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration
            .Builder(
                R.id.dictionary_bottom_nav,
                R.id.encyclopedia_bottom_nav,
                R.id.home_bottom_nav,
                R.id.profile_bottom_nav
            ).build()

        //if transition to dictionary is true immediately open dictionary fragment upon entering
        //else set the home icon as checked
        if (CompanionObject.transitionToDictionary) {
            navController.navigate(R.id.dictionary_bottom_nav)
            binding.bottomNav.selectedItemId = R.id.dictionary_bottom_nav
            CompanionObject.transitionToDictionary = false
        } else {
            binding.bottomNav.selectedItemId = R.id.home_bottom_nav
        }

        binding.bottomNav.setupWithNavController(navController)
        setSupportActionBar(binding.activityToolbar)
        with(binding.activityToolbar) {
            setNavigationOnClickListener { navController.navigateUp() }
            setupWithNavController(navController, appBarConfiguration)
        }
        binding.appBarLayout.setExpanded(false)
        binding.activityNestedScroll.isNestedScrollingEnabled = false

        val params = binding.appBarLayout.layoutParams as CoordinatorLayout.LayoutParams
        if (params.behavior == null)
            params.behavior = AppBarLayout.Behavior()
        val behaviour = params.behavior as AppBarLayout.Behavior

        //Hide BottomNav on fragments which aren't correlated to it
        navController.addOnDestinationChangedListener { _, destination, bundle ->

            binding.activityNestedScroll.scrollTo(0,0)

            binding.activityToolbarLayout.title =
                if (destination.id == R.id.dino_article_fragment) {
                    disableAppBarDrag(behaviour, bundle, true)
                    (bundle?.get("dinoSelected") as DinosaurEncyclopedia).name
                } else {
                    disableAppBarDrag(behaviour, draggable = false)
                    destination.label
                }

            binding.bottomNav.visibility = when (destination.id) {
                R.id.profile_edit_fragment -> {
                    binding.bottomNavHost.setMarginBottomNav(false)
                    View.GONE
                }
                R.id.dino_article_fragment -> {
                    binding.bottomNavHost.setMarginBottomNav(false)
                    View.GONE
                }
                else -> {
                    binding.bottomNavHost.setMarginBottomNav(true)
                    View.VISIBLE
                }
            }

        }
    }

    private fun disableAppBarDrag(
        behavior: AppBarLayout.Behavior,
        bundle: Bundle? = null,
        draggable: Boolean
    ) {
        binding.appBarLayout.setExpanded(draggable)
        binding.activityNestedScroll.isNestedScrollingEnabled = draggable

        behavior.setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
            override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                return draggable
            }
        })

        if(draggable) {
            binding.toolbarImage.setImageResource(
                (bundle?.get("dinoSelected") as DinosaurEncyclopedia).dinosaurFb
            )
        } else {
            binding.toolbarImage.setImageResource(0)
        }
    }

    private fun View.setMarginBottomNav(
        setBottomNavPadding: Boolean) {
        if(setBottomNavPadding) {
            setPadding(
                paddingLeft,
                paddingTop,
                paddingRight,
                (paddingBottom + binding.bottomNav.paddingBottom)
            )
        } else {
            setPadding(
                paddingLeft,
                paddingTop,
                paddingRight,
                originalFragmentPadding
            )
        }
    }
}

