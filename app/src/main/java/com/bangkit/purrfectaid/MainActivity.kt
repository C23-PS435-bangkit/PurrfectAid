package com.bangkit.purrfectaid

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bangkit.purrfectaid.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHost: NavHostFragment
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        navHost =
            supportFragmentManager.findFragmentById(binding.mainFragmentContainer.id) as NavHostFragment

        val inflater = navHost.navController.navInflater
        val graph = inflater.inflate(R.navigation.main_navigation)

        lifecycleScope.launch {
            if (viewModel.getToken() != null) {
                graph.setStartDestination(R.id.homeFragment)
                navHost.navController.graph = graph
            } else {
                graph.setStartDestination(R.id.openingFirstFragment)
                navHost.navController.graph = graph
            }
        }
        setContentView(binding.root)

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
                    R.id.profileFragment -> View.VISIBLE
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