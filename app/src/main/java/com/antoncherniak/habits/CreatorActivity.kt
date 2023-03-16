package com.antoncherniak.habits

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.antoncherniak.habits.model.Habit

class CreatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creator)

    }

    companion object {
        const val DEFAULT_POSITION = -1
        const val POSITION_KEY = "position_key"

        const val HABIT_EXTRA_KEY = "habit_extra_key"

        fun newIntent(context: Context, habit: Habit? = null, position: Int = DEFAULT_POSITION): Intent {
            val intent = Intent(context, CreatorActivity::class.java).apply {
                putExtra(POSITION_KEY, position)
                putExtra(HABIT_EXTRA_KEY, habit)
            }
            return intent
        }
    }

}