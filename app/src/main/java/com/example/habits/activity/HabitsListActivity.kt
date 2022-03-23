package com.example.habits.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.habits.App
import com.example.habits.R
import com.example.habits.adapter.HabitAdapter
import com.example.habits.databinding.ActivityHabitsListBinding
import com.example.habits.repository.MockRepository

class HabitsListActivity : AppCompatActivity() {

    private val viewBinding: ActivityHabitsListBinding by viewBinding()
    private val habitsRepository: MockRepository
        get() = (applicationContext as App).habitRepository
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
        habitsAdapter.data = habitsRepository.getHabits()
    }

    private fun setRecyclerViewSettings() {
        viewBinding.habitsRecyclerView.apply {
            adapter = habitsAdapter
            layoutManager = linearLayoutManager
        }
    }

    private fun addHabitButtonOnClick() {
        viewBinding.addFabButton.setOnClickListener {
            val intent = HabitCreatorActivity.newIntent(this)
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
        val intent = HabitCreatorActivity.newIntent(
            this,
            habit = habitsRepository.getHabits()[position],
            position = position
        )
        startActivity(intent)
    }

    private fun checkButtonClickListener(checkView: View, position: Int) {
        checkView.isSelected = !checkView.isSelected
        habitsRepository.setCheckForHabit(position)
    }

    private fun swipeToDelete() {
        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                habitsRepository.removeHabitAtPosition(viewHolder.bindingAdapterPosition)
                habitsAdapter.data = habitsRepository.getHabits()
            }

        }).attachToRecyclerView(viewBinding.habitsRecyclerView)
    }
}