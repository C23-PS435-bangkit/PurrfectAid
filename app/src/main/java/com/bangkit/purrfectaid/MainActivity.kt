package com.bangkit.purrfectaid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bangkit.purrfectaid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost =
            supportFragmentManager.findFragmentById(binding.mainFragmentContainer.id) as NavHostFragment
        binding.bottomNav.setupWithNavController(navHost.navController)

        navHost.navController.addOnDestinationChangedListener { _, destination, _ ->
            Handler(Looper.getMainLooper()).post {
                binding.bottomNav.visibility = when (destination.id) {
                    R.id.homeFragment -> View.VISIBLE
                    R.id.scanFragment -> View.VISIBLE
                    R.id.communityFragment -> View.VISIBLE
//                    R.id.profileFragment -> View.VISIBLE
                    R.id.vetFragment -> View.VISIBLE
                    else -> View.GONE
                }
            }
        }
    }
}