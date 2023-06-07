package com.bangkit.purrfectaid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bangkit.purrfectaid.databinding.ActivityMainBinding
import java.util.Stack

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHost: NavHostFragment
    private val fragmentStack = Stack<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHost =
            supportFragmentManager.findFragmentById(binding.mainFragmentContainer.id) as NavHostFragment
        binding.bottomNav.setupWithNavController(navHost.navController)

        binding.bottomNav.setOnItemSelectedListener {
            navHost.navController.navigate(it.itemId)
            return@setOnItemSelectedListener true
        }

        navHost.navController.addOnDestinationChangedListener { _, destination, _ ->
            Handler(Looper.getMainLooper()).post {
                binding.bottomNav.visibility = when (destination.id) {
                    R.id.homeFragment -> View.VISIBLE
                    R.id.vetFragment -> View.VISIBLE
                    R.id.communityFragment -> View.VISIBLE
                    else -> View.GONE
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = navHost.navController
        return navController.navigateUp()
    }
}