package com.antoncherniak.habits.habitslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.antoncherniak.habits.App
import com.antoncherniak.habits.R
import com.antoncherniak.habits.databinding.FragmentHabitListBinding
import com.antoncherniak.habits.habitcreator.HabitCreatorFragment
import com.antoncherniak.habits.habitslist.adapter.recyclerview.HabitListAdapter
import com.antoncherniak.habits.repository.MockRepository

class HabitListFragment : Fragment() {

    private val binding: FragmentHabitListBinding by viewBinding()
    private val habitAdapter: HabitListAdapter by lazy {
        HabitListAdapter { habitId ->
            openHabitForEditing(habitId)
        }
    }
    private val habitsRepository: MockRepository by lazy {
        (requireActivity().applicationContext as App).habitRepository
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_habit_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addHabitButtonOnClick()
        setRecyclerViewSettings()
        createAddButtonVisibilityBehavior()
        swipeToDelete()

        requireActivity().supportFragmentManager.setFragmentResultListener(
            REQUEST_ID_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            val resultId = bundle.getString(ID_RESULT_KEY)?.toInt() ?: 0
            val newItems = habitsRepository.getHabits()
            for (i in newItems.indices) {
                if (newItems[i].id == resultId) {
                    binding.habitRecyclerView.post {
                        binding.habitRecyclerView.layoutManager?.scrollToPosition(i)
                    }
                }
            }
        }
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
            findNavController().navigate(R.id.action_habitListViewPagerContainerFragment_to_habitCreatorFragment)
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

    private fun openHabitForEditing(habitId: Int) {
        findNavController().navigate(
            R.id.action_habitListViewPagerContainerFragment_to_habitCreatorFragment,
            HabitCreatorFragment.newBundle(
                habit = habitsRepository.getHabits().first { it.id == habitId },
                id = habitId
            )
        )
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
        val type = arguments?.getString(HABIT_TYPE_EXTRA_KEY)
        habitAdapter.submitList(habitsRepository.getHabits().filter { it.type.name == type })
    }


    companion object {
        private const val ID_RESULT_KEY = "id_res_key"
        private const val HABIT_TYPE_EXTRA_KEY = "habit type key"
        const val REQUEST_ID_KEY = "requestKey"

        fun newInstance(habitType: String): HabitListFragment =
            HabitListFragment().apply {
                arguments = Bundle().apply {
                    putString(
                        HABIT_TYPE_EXTRA_KEY,
                        habitType
                    )
                }
            }

        fun resultIdBundle(id: String): Bundle {
            return Bundle().apply {
                putString(ID_RESULT_KEY, id)
            }
        }
    }

}