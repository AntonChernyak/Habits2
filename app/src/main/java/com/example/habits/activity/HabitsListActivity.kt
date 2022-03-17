package com.example.habits.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.habits.R
import com.example.habits.adapter.HabitAdapter
import com.example.habits.databinding.ActivityHabitsListBinding
import com.example.habits.repository.MockRepository

class HabitsListActivity : AppCompatActivity() {

    private val viewBinding: ActivityHabitsListBinding by viewBinding()
    private val habitsAdapter: HabitAdapter by lazy {
        HabitAdapter({ position ->
            openHabitForEditing(position)
        }, { checkImageButton, position ->
            checkButtonClickListener(checkImageButton, position)
        })
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
        swipeToDelete()
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
                when {
                    dy >= 0 -> viewBinding.addFabButton.visibility = View.VISIBLE
                    dy < 0 -> viewBinding.addFabButton.visibility = View.GONE
                }
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

    private fun checkButtonClickListener(checkView: View, position: Int) {
        checkView.isSelected = !checkView.isSelected
        val isChecked = MockRepository.getHabits()[position].isChecked
        MockRepository.getHabits()[position].isChecked = !isChecked
    }

    private fun swipeToDelete(){
        ItemTouchHelper (object :ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                MockRepository.removeHabitAtPosition(viewHolder.bindingAdapterPosition)
                habitsAdapter.notifyItemRemoved(viewHolder.bindingAdapterPosition)
            }

        }).attachToRecyclerView(viewBinding.habitsRecyclerView)
    }

    companion object {
        const val HABIT_EXTRA_KEY = "habit_extra_key"
        const val POSITION_KEY = "position_key"
    }

}