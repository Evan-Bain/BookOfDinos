package com.example.dinoappv2

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.transition.Explode
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.example.dinoappv2.bottomNav.BottomNavActivity
import com.example.dinoappv2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setting exit transition
        with(window) {
            exitTransition = Explode()
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Going to main screen of app with bottom navigation view
        binding.startButton.setOnClickListener {
            val intent = Intent(this, BottomNavActivity::class.java)
            startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }
    }
}