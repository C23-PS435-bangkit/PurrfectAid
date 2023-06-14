package com.bangkit.purrfectaid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.view.marginBottom
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bangkit.purrfectaid.databinding.ActivityMainBinding
import com.bangkit.purrfectaid.presentation.scan.ScanFragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Stack

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHost: NavHostFragment
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val uri = intent.data
        if (uri != null) {
            Log.d("BErhasil", "BER")
        }

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

        binding.fab.setOnClickListener {
            hideBottomNavigation()
            supportFragmentManager.beginTransaction()
                .replace(binding.mainFragmentContainer.id, ScanFragment())
                .commit()
        }

        navHost.navController.addOnDestinationChangedListener { _, destination, _ ->
            Handler(Looper.getMainLooper()).post {
                when (destination.id) {
                    R.id.homeFragment -> {
                        showBottomNavigation()
                    }
                    R.id.vetFragment -> {
                        showBottomNavigation()
                    }
                    R.id.communityFragment -> showBottomNavigation()
                    else -> hideBottomNavigation()
                }
            }
        }
    }

    private fun showBottomNavigation() {
        binding.bottomNav.visibility = View.VISIBLE
        binding.mainFragmentContainer.setMarginBottom()
        binding.bottomAppBar.visibility = View.VISIBLE
        binding.fab.visibility = View.VISIBLE
    }

    private fun View.setMarginBottom() {
        val params = this.layoutParams as ViewGroup.MarginLayoutParams
        params.bottomMargin = 160
    }

    private fun View.removeMarginBottom() {
        val params = this.layoutParams as ViewGroup.MarginLayoutParams
        params.bottomMargin = 0
    }

    private fun hideBottomNavigation() {
        binding.bottomNav.visibility = View.GONE
        binding.mainFragmentContainer.removeMarginBottom()
        binding.bottomAppBar.visibility = View.GONE
        binding.fab.visibility = View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = navHost.navController
        return navController.navigateUp()
    }
}