package com.example.habits.presentation.activity

import android.view.View
import android.widget.ImageView
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.example.habits.R

class NavigationDrawerListeners(private val avatarImageView: ImageView) : DrawerLayout.DrawerListener {
    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
        Glide.with(avatarImageView.context)
            .load(AVATAR_URL)
            .centerCrop()
            .placeholder(R.drawable.app_logo)
            .error(R.drawable.no_avatar_image)
            .into(avatarImageView)
    }

    override fun onDrawerOpened(drawerView: View) {

    }

    override fun onDrawerClosed(drawerView: View) {
    }

    override fun onDrawerStateChanged(newState: Int) {
    }

    companion object{
        private const val AVATAR_URL = "https://vraki.net/sites/default/files/inline/images/prik-ava-2.jpg"
    }
}