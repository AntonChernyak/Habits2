package com.example.habits.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.habits.R
import com.example.habits.databinding.ActivityHabitCreatorBinding

class HabitCreatorActivity : AppCompatActivity() {

    private val viewBinding: ActivityHabitCreatorBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_creator)

    }

}