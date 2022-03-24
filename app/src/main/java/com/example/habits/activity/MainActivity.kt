package com.example.habits.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.habits.R
import com.example.habits.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val binding: ActivityMainBinding by viewBinding()
    private lateinit var navController: NavController
    private lateinit var appBarConfig: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        appBarConfig = AppBarConfiguration(navController.graph, binding.drawerLayout)

        binding.navigationDrawerView.setupWithNavController(navController)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.applicationInfoFragment -> {
                navController.navigate(R.id.applicationInfoFragment)
            }
            R.id.viewPagerContainerFragment -> {
                navController.navigate(
                    R.id.viewPagerContainerFragment,
                    null
                )
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}