package com.antoncherniak.habits.presentation.habitslist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.distinctUntilChanged
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.antoncherniak.habits.R
import com.antoncherniak.habits.databinding.FragmentHabitListBinding
import com.antoncherniak.habits.domain.model.HabitModel
import com.antoncherniak.habits.presentation.habitslist.adapter.recyclerview.HabitListAdapter
import com.antoncherniak.habits.domain.model.HabitType
import com.antoncherniak.habits.presentation.extensions.viewModelFactory
import com.antoncherniak.habits.presentation.habitcreator.HabitCreatorFragment.Companion.DEFAULT_HABIT_ID
import com.antoncherniak.habits.presentation.habitslist.adapter.recyclerview.SwipeToDeleteCallback
import com.google.android.material.snackbar.Snackbar


class HabitListFragment : Fragment() {

    private val binding: FragmentHabitListBinding by viewBinding()
    private val habitAdapter: HabitListAdapter by lazy {
        HabitListAdapter { habitId ->
            openHabitForEditing(habitId)
        }
    }
    private val viewModel: HabitListViewModel by activityViewModels { viewModelFactory() }

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
        binding.habitRecyclerView.adapter = habitAdapter
        viewModel.screenState.observe(viewLifecycleOwner, ::listFragmentUiRender)
        viewModel.searchSettings
            .distinctUntilChanged()
            .observe(viewLifecycleOwner) {
                viewModel.getHabits()
            }

        binding.listSwipeToRefresh.setOnRefreshListener {
            viewModel.getHabits()
            binding.listSwipeToRefresh.isRefreshing = false
        }
    }

    override fun onResume() {
        super.onResume()
        setScrollToEditedHabitPositionSettings()
        viewModel.setHabitType(
            arguments?.getString(HABIT_TYPE_EXTRA_KEY) ?: HabitType.GOOD_HABIT.name
        )
    }

    private fun listFragmentUiRender(screenState: ListScreenState) {
        when (screenState) {
            is ListScreenState.Data -> {
                with(binding) {
                    shimmerContainer.isVisible = false
                    habitRecyclerView.isVisible = true
                    errorImageView.isVisible = false
                }
                val dataList = mutableListOf<HabitModel>().apply { addAll(screenState.habits) }
                habitAdapter.submitList(dataList)
                binding.habitRecyclerView.post {
                    binding.habitRecyclerView.layoutManager?.scrollToPosition(0)
                }
            }
            ListScreenState.Loading -> {
                with(binding) {
                    shimmerContainer.isVisible = true
                    habitRecyclerView.isVisible = false
                    errorImageView.isVisible = false
                }
            }
            is ListScreenState.Error -> {
                with(binding) {
                    shimmerContainer.isVisible = false
                    habitRecyclerView.isVisible = false
                    errorImageView.isVisible = true
                }
                Snackbar.make(
                    binding.errorImageView,
                    getString(R.string.error_message),
                    Snackbar.LENGTH_LONG
                )
                    .show()
                Log.e(ERROR_TAG, screenState.errorMessage)
            }
            ListScreenState.Init -> {}
        }
    }

    private fun setScrollToEditedHabitPositionSettings() {
        requireActivity().supportFragmentManager.setFragmentResultListener(
            REQUEST_ID_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            val resultId = bundle.getString(ID_RESULT_KEY)?.toInt() ?: 0
            binding.habitRecyclerView.post {
                val newPosition = habitAdapter.getItemPositionById(resultId)
                binding.habitRecyclerView.layoutManager?.scrollToPosition(newPosition)
            }
        }
    }

    private fun addHabitButtonOnClick() {
        val directions = HabitListViewPagerContainerFragmentDirections
            .actionHabitListViewPagerContainerFragmentToHabitCreatorFragment(DEFAULT_HABIT_ID)
        binding.addNewHabitButton.setOnClickListener {
            findNavController().navigate(directions)
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
        val directions = HabitListViewPagerContainerFragmentDirections
            .actionHabitListViewPagerContainerFragmentToHabitCreatorFragment(habitId)
        findNavController().navigate(directions)
    }

    private fun swipeToDelete() {
        ItemTouchHelper(object : SwipeToDeleteCallback(requireActivity()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.removeHabit(
                    habitId = habitAdapter.getItemByPosition(viewHolder.adapterPosition).id
                )
            }
        }).attachToRecyclerView(binding.habitRecyclerView)
    }

    companion object {
        private const val ID_RESULT_KEY = "id_res_key"
        private const val HABIT_TYPE_EXTRA_KEY = "habit type key"
        private const val ERROR_TAG = "ERROR: "
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