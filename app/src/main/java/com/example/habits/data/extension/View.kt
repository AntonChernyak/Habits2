package com.example.habits.data.extension

import android.graphics.Color
import android.view.View

fun View.getBackgroundColor(): Int {
    return backgroundTintList?.defaultColor ?: Color.MAGENTA
}