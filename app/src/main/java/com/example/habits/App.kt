package com.example.habits

import android.app.Application
import com.example.habits.repository.MockRepository

class App: Application() {

    val habitRepository = MockRepository()
}