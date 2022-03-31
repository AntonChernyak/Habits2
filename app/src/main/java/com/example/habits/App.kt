package com.example.habits

import android.app.Application
import com.example.habits.data.repository.MockRepository

class App: Application() {

    val habitRepository = MockRepository()
}