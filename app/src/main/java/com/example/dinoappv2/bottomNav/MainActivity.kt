package com.example.dinoappv2.bottomNav

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.dinoappv2.R
import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia
import com.example.dinoappv2.databases.BackgroundImageDatabase
import com.example.dinoappv2.databinding.ActivityMainBinding
import com.example.dinoappv2.viewModels.MainViewModel
import com.example.dinoappv2.viewModels.MainViewModelFactory
import com.google.android.material.appbar.AppBarLayout

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    //enables layout to include more padding if there bottomNav is present (& vice versa)
    private var originalFragmentPadding = 0

    //shared by "SelectBackgroundFragment" & "ProfileFragment"
    private val mainViewModel: MainViewModel by viewModels { getViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setBackground()
        val splashScreen = installSplashScreen()

        //add fade animation when splashScreen is ready to exit
        splashScreen.setOnExitAnimationListener { splash ->
            ObjectAnimator.ofFloat(splash.view, "alpha", 0f).apply {
                duration = 1000
                interpolator = AccelerateDecelerateInterpolator()
                this.doOnEnd {
                    splash.remove()
                }
                start()
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //gets padding for bottom of fragments associated with the bottomNav to avoid
        //collisions
        originalFragmentPadding = binding.bottomNavHost.paddingBottom

        //called when background is changed within SelectBackgroundFragment
        mainViewModel.backgroundChanged.observe(this) {
            if(mainViewModel.backgroundSet) {

                //set false to prevent continuously recreating app
                mainViewModel.setBackground(false)

                //recreates fragment to reset the theme
                finish()
                overridePendingTransition(0,0)
                startActivity(intent)
            }
        }

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
                R.id.select_background_fragment -> {
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
        //defaults to 'normal toolbar' if location is not on DinoArticleFragment
        binding.appBarLayout.setExpanded(draggable)

        //disables dragging on toolbar to expand/collapse if on any location other
        //than DinoArticleFragment
        behavior.setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
            override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                return draggable
            }
        })

        //sets dinosaur image in collapsable toolbar if selected fragment is
        //DinoArticleFragment, otherwise no image is selected
        if (draggable) {
            binding.toolbarImage.setImageResource(
                (bundle?.get("dinoSelected") as DinosaurEncyclopedia).dinosaurFb
            )
        } else {
            binding.toolbarImage.setImageResource(0)
        }
    }

    /** automatically adds or removes padding depending on if fragment is connected to the
     *  bottomNav **/
    private fun View.setMarginBottomNav(
        setBottomNavPadding: Boolean
    ) {
        if (setBottomNavPadding) {
            //bottomNav is present
            setPadding(
                paddingLeft,
                paddingTop,
                paddingRight,
                (paddingBottom + binding.bottomNav.paddingBottom)
            )
        } else {
            //bottomNav isn't present
            setPadding(
                paddingLeft,
                paddingTop,
                paddingRight,
                originalFragmentPadding
            )
        }
    }

    /** sets background of app depending on what style is currently selected **/
    private fun setBackground(): Boolean {
        return if(mainViewModel.backgroundSet) {
            //if background is changed while app is in runtime
            //retrieves data saved in mainViewModel
            theme.applyStyle(
                when(mainViewModel.backgroundChanged.value) {
                    0 -> R.style.LandStyle
                    1 -> R.style.WaterStyle
                    2 -> R.style.SkyStyle
                    else -> R.style.DefaultStyle
                }, true
            )
            true
        } else {
            //if app is first launched
            //retrieves data from BackgroundImageDatabase
            mainViewModel.backgroundImage?.let { background ->
                theme.applyStyle(
                    when(background.backgroundImage) {
                        0 -> R.style.LandStyle
                        1 -> R.style.WaterStyle
                        2 -> R.style.SkyStyle
                        else -> R.style.DefaultStyle
                    }, true
                )
                return true
            }
            theme.applyStyle(R.style.DefaultStyle, true)
            false
        }
    }

    private fun getViewModelFactory(): MainViewModelFactory {
        val database = BackgroundImageDatabase.getInstance(this).backgroundImageDao
        return MainViewModelFactory(database)
    }
}

