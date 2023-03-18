package com.antoncherniak.habits

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.antoncherniak.habits.databinding.ActivityListBinding
import com.antoncherniak.habits.habitslist.HabitListAdapter
import com.antoncherniak.habits.repository.MockRepository

class ListActivity : AppCompatActivity() {

    private val binding: ActivityListBinding by viewBinding()
    private val habitAdapter: HabitListAdapter by lazy {
        HabitListAdapter { position ->
            openHabitForEditing(position)
        }
    }
    private val habitsRepository: MockRepository by lazy {
        (applicationContext as App).habitRepository
    }

    private val creatorActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val id: Int = result.data?.getStringExtra(ID_RESULT_KEY)?.toInt() ?: 0
                val newItems = habitsRepository.getHabits()
                for (i in newItems.indices) {
                    if (newItems[i].id == id) {
                        binding.habitRecyclerView.apply {
                            post {
                                smoothScrollToPosition(i)
                            }
                        }
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        addHabitButtonOnClick()
        setRecyclerViewSettings()
        createAddButtonVisibilityBehavior()
        swipeToDelete()
    }

    override fun onResume() {
        super.onResume()
        updateHabitsData()
    }

    private fun setRecyclerViewSettings() {
        binding.habitRecyclerView.apply {
            adapter = habitAdapter
        }
    }

    private fun addHabitButtonOnClick() {
        binding.addNewHabitButton.setOnClickListener {
            CreatorActivity.newIntent(this@ListActivity).apply {
                creatorActivityResult.launch(this)
            }
        }
    }

    private fun createAddButtonVisibilityBehavior() {
        binding.habitRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                binding.addNewHabitButton.isVisible = dy >= 0
            }
        })
    }

    private fun openHabitForEditing(position: Int) {
        CreatorActivity.newIntent(
            context = this@ListActivity,
            habit = habitsRepository.getHabits()[position],
            position = position
        ).apply { creatorActivityResult.launch(this) }
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
                habitsRepository.removeHabitAtPosition(viewHolder.adapterPosition)
                updateHabitsData()
            }
        }).attachToRecyclerView(binding.habitRecyclerView)
    }

    private fun updateHabitsData() {
        habitAdapter.submitList(habitsRepository.getHabits())
    }

    companion object {
        const val ID_RESULT_KEY = "id_res_key"

        fun newIntent(habitId: String): Intent {
            return Intent().putExtra(ID_RESULT_KEY, habitId)
        }
    }

}