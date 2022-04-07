package com.example.habits.presentation.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.habits.R
import com.example.habits.presentation.adapter.HabitAdapter
import com.example.habits.databinding.FragmentHabitsListBinding
import com.example.habits.data.model.HabitType
import com.example.habits.data.extension.addToggleToNavigationDrawer
import com.example.habits.data.extension.afterTextChanged
import com.example.habits.data.extension.factory
import com.example.habits.data.model.HabitItem
import com.google.android.material.bottomsheet.BottomSheetBehavior

class HabitsListFragment : Fragment() {

    private val viewBinding: FragmentHabitsListBinding by viewBinding()
    private val habitsAdapter: HabitAdapter by lazy {
        HabitAdapter({ position ->
            openHabitForEditing(position)
        }, { checkImageButton, position ->
            checkButtonClickListener(checkImageButton, position)
        })
    }
    private val habitsListViewModel: HabitsListViewModel by viewModels { factory() }
    private var items = listOf<HabitItem>()
    private var reversed = true
    private val bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout> by lazy {
        BottomSheetBehavior.from(viewBinding.searchBottomSheet)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_habits_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addHabitButtonOnClick()
        setRecyclerViewSettings()
        createAddButtonVisibilityBehavior()
        swipeToDelete()
        requireActivity().addToggleToNavigationDrawer(
            R.id.drawer_layout,
            R.id.habitsListToolbar,
            R.string.navigation_open,
            R.string.navigation_close
        )

        val type = arguments?.getParcelable<HabitType>(ITEMS_TYPE_EXTRA)

        habitsListViewModel.habitsLiveData.observe(viewLifecycleOwner) { list ->
            if (type != null) {
                habitsAdapter.data = if (type == HabitType.BAD_HABIT) {
                    items = list.filter { it.type == HabitType.BAD_HABIT }
                    items
                } else {
                    items = list.filter { it.type == HabitType.GOOD_HABIT }
                    items
                }
            }
        }

        viewBinding.searchEditText.afterTextChanged {
            habitsListViewModel.getSearchList(viewBinding.searchEditText.text.toString().trim())
            viewBinding.habitsRecyclerView.layoutManager?.scrollToPosition(0)
        }

        createHabitSortSpinner()
        setSortItemSpinnerClickListener()
        setSortButtonsBehaviour()
    }

    override fun onResume() {
        super.onResume()
        val sortedSpinnerPosition = viewBinding.sortSpinner.selectedItemPosition
        habitsListViewModel.getSortedHabits(sortedSpinnerPosition, reversed)
    }

    override fun onPause() {
        super.onPause()
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun setRecyclerViewSettings() {
        viewBinding.habitsRecyclerView.apply {
            adapter = habitsAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        }
    }

    private fun addHabitButtonOnClick() {
        viewBinding.addFabButton.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(R.id.action_viewPagerContainerFragment_to_habitCreatorFragment, null)
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
        val bundle = Bundle().apply {
            putParcelable(HABIT_EXTRA_KEY, items[position])
            putInt(POSITION_KEY, position)
        }
        findNavController().navigate(
            R.id.action_viewPagerContainerFragment_to_habitCreatorFragment,
            bundle
        )
    }

    private fun checkButtonClickListener(checkView: View, position: Int) {
        checkView.isSelected = !checkView.isSelected
        habitsListViewModel.setCheckForHabit(items[position])
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
                val removedHabit = items[viewHolder.bindingAdapterPosition]
                habitsListViewModel.removeHabit(removedHabit)
            }

        }).attachToRecyclerView(viewBinding.habitsRecyclerView)
    }

    private fun createHabitSortSpinner() {
        val spinnerAdapter = ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.sortingSpinnerDataArray,
            android.R.layout.simple_spinner_item
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        viewBinding.sortSpinner.adapter = spinnerAdapter
    }
    
    private fun setSortItemSpinnerClickListener(){
        viewBinding.sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                habitsListViewModel.getSortedHabits(p2, reversed)
                viewBinding.habitsRecyclerView.layoutManager?.scrollToPosition(0)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

    }

    private fun setSortButtonsBehaviour(){
        val sortedSpinnerPosition = viewBinding.sortSpinner.selectedItemPosition
        viewBinding.buttonUp.setOnClickListener {
            reversed = false
            habitsListViewModel.getSortedHabits(sortedSpinnerPosition, reversed)
            viewBinding.habitsRecyclerView.layoutManager?.scrollToPosition(0)
        }
        viewBinding.buttonDown.setOnClickListener {
            reversed = true
            habitsListViewModel.getSortedHabits(sortedSpinnerPosition, reversed)
            viewBinding.habitsRecyclerView.layoutManager?.scrollToPosition(0)
        }
    }

    companion object {
        const val HABIT_EXTRA_KEY = "habit_extra_key"
        const val POSITION_KEY = "position_key"
        const val ITEMS_TYPE_EXTRA = "items_list_extra"

        fun newInstance(type: HabitType): HabitsListFragment {
            return HabitsListFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ITEMS_TYPE_EXTRA, type)
                }
            }
        }
    }
}