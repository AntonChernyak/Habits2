package com.example.habits.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.habits.R
import com.example.habits.adapter.HabitAdapter
import com.example.habits.databinding.ActivityHabitsListBinding
import com.example.habits.repository.MockRepository

class HabitsListActivity : AppCompatActivity() {

    private val viewBinding: ActivityHabitsListBinding by viewBinding()
    private val habitsAdapter = HabitAdapter { position ->
        openHabitForEditing(position)
    }
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habits_list)
        addHabitButtonOnClick()
        setRecyclerViewSettings()
        createAddButtonVisibilityBehavior()
    }

    override fun onResume() {
        super.onResume()
        habitsAdapter.data = MockRepository.getHabits()
    }

    private fun setRecyclerViewSettings() {
        viewBinding.habitsRecyclerView.apply {
            adapter = habitsAdapter
            layoutManager = linearLayoutManager
        }
    }

    private fun addHabitButtonOnClick() {
        viewBinding.addFabButton.setOnClickListener {
            val intent = Intent(this@HabitsListActivity, HabitCreatorActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createAddButtonVisibilityBehavior() {
        viewBinding.habitsRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val lastPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                val habitsCount = MockRepository.getHabits().size
                if (habitsCount > ADD_BUTTON_VISIBILITY_MARK && lastPosition == habitsCount - 1) {
                    viewBinding.addFabButton.visibility = View.GONE
                } else viewBinding.addFabButton.visibility = View.VISIBLE
            }
        }
        )
    }

    private fun openHabitForEditing(position: Int) {
        val intent = Intent(this, HabitCreatorActivity::class.java).apply {
            putExtra(HABIT_EXTRA_KEY, MockRepository.getHabits()[position])
            putExtra(POSITION_KEY, position)
        }
        startActivity(intent)
    }

    companion object {
        const val ADD_BUTTON_VISIBILITY_MARK = 4
        const val HABIT_EXTRA_KEY = "habit_extra_key"
        const val POSITION_KEY = "position_key"
    }

}