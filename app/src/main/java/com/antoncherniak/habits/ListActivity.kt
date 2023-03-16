package com.antoncherniak.habits

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.antoncherniak.habits.CreatorActivity.Companion.ID_RESULT_KEY
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
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
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
                        binding.habitRecyclerView.scrollToPosition(i)
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
            layoutManager = linearLayoutManager
        }
    }

    private fun addHabitButtonOnClick() {
        binding.addNewHabitButton.setOnClickListener {
            val intent = CreatorActivity.newIntent(this)
            creatorActivityResult.launch(intent)
        }
    }

    private fun createAddButtonVisibilityBehavior() {
        binding.habitRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy < 0) {
                    binding.addNewHabitButton.visibility = View.GONE
                } else binding.addNewHabitButton.visibility = View.VISIBLE
            }
        })
    }

    private fun openHabitForEditing(position: Int) {
        val intent = CreatorActivity.newIntent(
            this,
            habit = habitsRepository.getHabits()[position],
            position = position
        )
        creatorActivityResult.launch(intent)
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

}