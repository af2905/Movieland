package com.github.af2905.movieland.presentation

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.github.af2905.movieland.R
import com.github.af2905.movieland.data.worker.UpdateMoviesWorker
import com.github.af2905.movieland.databinding.ActivityMainBinding
import com.github.af2905.movieland.di.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<MainViewModel>

    lateinit var viewModel: MainViewModel
    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController

    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = Color.TRANSPARENT

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        binding.viewModel = viewModel

        navController = findNavController()

        setupBottomNavMenu(navController)
        setDestinationChangedListener()

        val workRequest = OneTimeWorkRequestBuilder<UpdateMoviesWorker>()
            .build()

        WorkManager.getInstance(this)
            .enqueueUniqueWork(
                UpdateMoviesWorker.WORKER_NAME,
                ExistingWorkPolicy.REPLACE,
                workRequest
            )
    }

    private fun findNavController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }

    private fun setupBottomNavMenu(navController: NavController) {
        bottomNav = findViewById(R.id.bottom_nav_view)
        bottomNav.setupWithNavController(navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.homeFragment -> {
                navController.popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    private fun setDestinationChangedListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment, R.id.searchFragment, R.id.profileFragment -> showBottomNav()
                else -> hideBottomNav()
            }
        }
    }

    private fun showBottomNav() {
        bottomNav.visibility = View.VISIBLE

    }

    private fun hideBottomNav() {
        bottomNav.visibility = View.GONE
    }
}