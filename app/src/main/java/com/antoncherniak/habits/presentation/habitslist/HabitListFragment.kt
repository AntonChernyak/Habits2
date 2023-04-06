package com.antoncherniak.habits.presentation.habitslist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.antoncherniak.habits.R
import com.antoncherniak.habits.databinding.FragmentHabitListBinding
import com.antoncherniak.habits.domain.model.HabitModel
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
    private var reversed = false

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
        createHabitSortSpinner()
        setSortButtonsBehaviour()
        setSortItemSpinnerClickListener()
        onRestoreInstanceState(savedInstanceState)

        binding.habitRecyclerView.adapter = habitAdapter
        viewModel.screenState.observe(viewLifecycleOwner, ::listFragmentUiRender)

        binding.searchBottomSheet.searchEditText.doAfterTextChanged {
            viewModel.searchAndSortHabits(it.toString())
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            putString(SEARCH_KEY, binding.searchBottomSheet.searchEditText.text.toString())
            putBoolean(REVERSED_KEY, reversed)
            putInt(SORT_TYPE_KEY, binding.searchBottomSheet.sortSpinner.selectedItemPosition)
        }
    }

    private fun onRestoreInstanceState(outState: Bundle?) {
        with(binding.searchBottomSheet) {
            searchEditText.setText(outState?.getString(SEARCH_KEY, "") ?: "")
            reversed = outState?.getBoolean(REVERSED_KEY) ?: false
            sortSpinner.setSelection(outState?.getInt(SORT_TYPE_KEY, 0) ?: 0)
        }
    }

    private fun listFragmentUiRender(screenState: ListScreenState) {
        when (screenState) {
            is ListScreenState.Data -> {
                with(binding) {
                    shimmerContainer.isVisible = false
                    habitRecyclerView.isVisible = true
                    errorImageView.isVisible = false
                }
                val dataList = mutableListOf<HabitModel>().apply { addAll(screenState.habits)}
                habitAdapter.submitList(dataList)
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
                Log.e(ERROR_TAG, screenState.errorMessage)
            }
            ListScreenState.Init -> {
                val type = arguments?.getString(HABIT_TYPE_EXTRA_KEY) ?: HabitType.GOOD_HABIT.name
                viewModel.getHabits(type,
                    query = binding.searchBottomSheet.searchEditText.text.toString(),
                    sortType = binding.searchBottomSheet.sortSpinner.selectedItemPosition,
                    reversed = reversed
                )
            }
        }
    }

    private fun setScrollToEditedHabitPositionSettings() {
        requireActivity().supportFragmentManager.setFragmentResultListener(
            REQUEST_ID_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
           val type = arguments?.getString(HABIT_TYPE_EXTRA_KEY) ?: HabitType.GOOD_HABIT.name
            viewModel.getHabits(type,
                query = binding.searchBottomSheet.searchEditText.text.toString(),
                sortType = binding.searchBottomSheet.sortSpinner.selectedItemPosition,
                reversed = reversed)
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

    private fun createHabitSortSpinner() {
        val spinnerAdapter = ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.sortingSpinnerDataArray,
            android.R.layout.simple_spinner_item
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.searchBottomSheet.sortSpinner.adapter = spinnerAdapter
    }

    private fun setSortItemSpinnerClickListener() {
        binding.searchBottomSheet.sortSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    getSortedHabits()
                    binding.habitRecyclerView.post {
                        binding.habitRecyclerView.layoutManager?.scrollToPosition(0)
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }

    }

    private fun setSortButtonsBehaviour() {
        with(binding) {
            searchBottomSheet.buttonUp.setOnClickListener {
                searchBottomSheet.buttonUp.background = ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.background_sort_button_selected
                )
                searchBottomSheet.buttonDown.background = ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.background_sort_button_not_selected
                )
                reversed = false
                getSortedHabits()
                binding.habitRecyclerView.post {
                    binding.habitRecyclerView.layoutManager?.scrollToPosition(0)
                }
            }
            searchBottomSheet.buttonDown.setOnClickListener {
                searchBottomSheet.buttonDown.background = ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.background_sort_button_selected
                )
                searchBottomSheet.buttonUp.background = ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.background_sort_button_not_selected
                )
                reversed = true
                getSortedHabits()
                binding.habitRecyclerView.post {
                    binding.habitRecyclerView.layoutManager?.scrollToPosition(0)
                }
            }
        }
    }

    private fun getSortedHabits() {
        viewModel.searchAndSortHabits(
            query = binding.searchBottomSheet.searchEditText.text.toString(),
            sortType = binding.searchBottomSheet.sortSpinner.selectedItemPosition,
            reversed = reversed
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
                viewModel.removeHabit(
                    habit = habitAdapter.getItemByPosition(viewHolder.adapterPosition),
                    query = binding.searchBottomSheet.searchEditText.text.toString(),
                    sortType = binding.searchBottomSheet.sortSpinner.selectedItemPosition,
                    reversed = reversed
                )
            }
        }).attachToRecyclerView(binding.habitRecyclerView)
    }

    companion object {
        private const val ID_RESULT_KEY = "id_res_key"
        private const val SEARCH_KEY = "search_key"
        private const val REVERSED_KEY = "reversed_key"
        private const val SORT_TYPE_KEY = "sort_key"
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