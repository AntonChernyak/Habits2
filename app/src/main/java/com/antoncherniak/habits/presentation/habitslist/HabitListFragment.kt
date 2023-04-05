package com.antoncherniak.habits.presentation.habitslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.antoncherniak.habits.R
import com.antoncherniak.habits.databinding.FragmentHabitListBinding
import com.antoncherniak.habits.presentation.habitcreator.HabitCreatorFragment
import com.antoncherniak.habits.presentation.habitslist.adapter.recyclerview.HabitListAdapter
import com.antoncherniak.habits.domain.model.HabitType
import com.antoncherniak.habits.presentation.extensions.viewModelFactory

class HabitListFragment : Fragment() {

    private val binding: FragmentHabitListBinding by viewBinding()
    private val habitAdapter: HabitListAdapter by lazy {
        HabitListAdapter { habitId ->
            openHabitForEditing(habitId)
        }
    }
    private val viewModel: HabitListViewModel by viewModels { viewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_habit_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addHabitButtonOnClick()
        createAddButtonVisibilityBehavior()
        swipeToDelete()
        setScrollToEditedHabitPositionSettings()

        binding.habitRecyclerView.adapter = habitAdapter
        viewModel.screenState.observe(viewLifecycleOwner, ::listFragmentUiRender)
    }

    private fun listFragmentUiRender(screenState: ListScreenState) {
        when (screenState) {
            is ListScreenState.Data -> {
                habitAdapter.submitList(screenState.habits)
            }
            ListScreenState.Loading -> {}
            is ListScreenState.Error -> {}
            ListScreenState.Init -> {
                val type = arguments?.getString(HABIT_TYPE_EXTRA_KEY) ?: HabitType.GOOD_HABIT.name
                viewModel.getHabits(type)
            }
        }
    }

    private fun setScrollToEditedHabitPositionSettings() {
        requireActivity().supportFragmentManager.setFragmentResultListener(
            REQUEST_ID_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            val resultId = bundle.getString(ID_RESULT_KEY)?.toInt() ?: 0
            val newPosition = habitAdapter.getItemPositionById(resultId)
            binding.habitRecyclerView.post {
                binding.habitRecyclerView.layoutManager?.scrollToPosition(newPosition)
            }
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
                habit = habitAdapter.getItemById(habitId),
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
                viewModel.removeHabit(habitAdapter.getItemByPosition(viewHolder.adapterPosition))
            }
        }).attachToRecyclerView(binding.habitRecyclerView)
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