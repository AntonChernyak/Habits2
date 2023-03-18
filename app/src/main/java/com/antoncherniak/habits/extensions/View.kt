package com.antoncherniak.habits.extensions

import android.graphics.Color
import android.view.View

fun View.getBackgroundColor(): Int {
    return backgroundTintList?.defaultColor ?: Color.WHITE
}