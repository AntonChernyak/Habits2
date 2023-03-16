package com.antoncherniak.habits

import android.app.Application
import com.antoncherniak.habits.repository.MockRepository

class App: Application() {

    val habitRepository = MockRepository()
}