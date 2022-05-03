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
import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia
import com.example.dinoappv2.databinding.ActivityMainBinding
import com.google.android.material.appbar.AppBarLayout

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var originalFragmentPadding = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        originalFragmentPadding = binding.bottomNavHost.paddingBottom

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.bottom_nav_host) as NavHostFragment
        navController = navHostFragment.navController

        //sets every fragment in the bottomNav layout as a top level fragment on the backstack
        val appBarConfiguration = AppBarConfiguration
            .Builder(
                R.id.dictionary_bottom_nav,
                R.id.encyclopedia_bottom_nav,
                R.id.home_bottom_nav,
                R.id.profile_bottom_nav
            ).build()

        //set up navController with Toolbar and bottomNav
        binding.bottomNav.setupWithNavController(navController)
        setSupportActionBar(binding.activityToolbar)
        with(binding.activityToolbar) {
            setNavigationOnClickListener { navController.navigateUp() }
            setupWithNavController(navController, appBarConfiguration)
        }

        //automatically collapse toolbar
        binding.appBarLayout.setExpanded(false)

        //sets up default code to disable/enable collapsable toolbar
        val params = binding.appBarLayout.layoutParams as CoordinatorLayout.LayoutParams
        if (params.behavior == null)
            params.behavior = AppBarLayout.Behavior()
        val behaviour = params.behavior as AppBarLayout.Behavior

        navController.addOnDestinationChangedListener { _, destination, bundle ->

            //determines if toolbar should be collapsable and sets the toolbar title for
            //DinoArticle to the Dinosaur clicked
            binding.activityToolbarLayout.title =
                if (destination.id == R.id.dino_article_fragment) {
                    disableAppBarDrag(behaviour, bundle, true)
                    (bundle?.get("dinoSelected") as DinosaurEncyclopedia).name
                } else {
                    disableAppBarDrag(behaviour, draggable = false)
                    destination.label
                }

            //disables bottomNav and padding on fragments not connected to bottomNav layout
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

    /** Disable/enable collapsable toolbar and automatically sets toolbar dinosaur image **/
    private fun disableAppBarDrag(
        behavior: AppBarLayout.Behavior,
        bundle: Bundle? = null,
        draggable: Boolean
    ) {
        binding.appBarLayout.setExpanded(draggable)

        behavior.setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
            override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                return draggable
            }
        })

        //sets dinosaur image if on DinoArticle and no image if not
        if (draggable) {
            binding.toolbarImage.setImageResource(
                (bundle?.get("dinoSelected") as DinosaurEncyclopedia).dinosaurFb
            )
        } else {
            binding.toolbarImage.setImageResource(0)
        }
    }

    /** Automatically adds or removes padding depending on if fragment is connected to the
     *  bottomNav **/
    private fun View.setMarginBottomNav(
        setBottomNavPadding: Boolean
    ) {
        if (setBottomNavPadding) {
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

