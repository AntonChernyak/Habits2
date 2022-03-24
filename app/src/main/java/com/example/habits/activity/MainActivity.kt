package com.example.habits.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.habits.R
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }
}