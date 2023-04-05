package com.antoncherniak.habits

import android.app.Application
import com.antoncherniak.habits.data.repository.MockRepository

class App: Application() {

    val habitRepository = MockRepository()
}