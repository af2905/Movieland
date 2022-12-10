package com.github.af2905.movieland

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.github.af2905.movieland.core.data.worker.UpdateMoviesWorker
import com.github.af2905.movieland.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController

    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        navController = findNavController()

        setupBottomNavMenu(navController)

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
}