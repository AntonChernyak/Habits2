package com.example.habits.presentation.extension

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout

fun Activity.hideKeyboard() {
    this.currentFocus?.let { view ->
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Activity.addToggleToNavigationDrawer(
    @IdRes drawerLayout: Int,
    @IdRes toolbar: Int,
    @StringRes openDrawerContent: Int,
    @StringRes closeDrawerContent: Int
) {
    val drawer = this.findViewById<DrawerLayout>(drawerLayout)
    val toggle = ActionBarDrawerToggle(
        this, drawer, this.findViewById(toolbar),
        openDrawerContent, closeDrawerContent
    )
    drawer.addDrawerListener(toggle)
    toggle.syncState()
}
