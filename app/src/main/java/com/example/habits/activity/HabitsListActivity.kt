package com.example.habits.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.habits.R
import com.example.habits.adapter.HabitAdapter
import com.example.habits.databinding.ActivityHabitsListBinding
import com.example.habits.repository.MockRepository

class HabitsListActivity : AppCompatActivity() {

    private val viewBinding: ActivityHabitsListBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habits_list)

        viewBinding.habitsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@HabitsListActivity, LinearLayoutManager.VERTICAL, false)
            val habitsAdapter = HabitAdapter()
            habitsAdapter.data = MockRepository.list
            adapter = habitsAdapter
        }

        addHabitButtonOnClick()
    }

    private fun addHabitButtonOnClick(){
        viewBinding.addFabButton.setOnClickListener {
            val intent = Intent(this@HabitsListActivity, HabitCreatorActivity::class.java)
            startActivity(intent)
        }
    }
}