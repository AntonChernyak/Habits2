package com.antoncherniak.habits.presentation.habitslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.antoncherniak.habits.R
import com.antoncherniak.habits.databinding.FragmentSearchAndSortBinding
import com.antoncherniak.habits.presentation.extensions.OnSpinnerItemSelectedListener
import com.antoncherniak.habits.presentation.extensions.viewModelFactory

class SearchAndSortFragment : Fragment() {

    private val viewModel: HabitListViewModel by activityViewModels { viewModelFactory() }
    private val binding: FragmentSearchAndSortBinding by viewBinding()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_and_sort, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSortItemSpinnerClickListener()
        createHabitSortSpinner()
        setBottomNavigationViewsSettings()
    }

    override fun onResume() {
        super.onResume()
        binding.searchEditText.setText(viewModel.getSearchString())
        binding.sortSpinner.setSelection(viewModel.getSortedSpinnerPosition())
        setInitSortButtonColor(binding.buttonDown, binding.buttonUp)
    }

    private fun createHabitSortSpinner() {
        val spinnerAdapter = ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.sortingSpinnerDataArray,
            android.R.layout.simple_spinner_item
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sortSpinner.adapter = spinnerAdapter
    }

    private fun setSortItemSpinnerClickListener() {
        binding.sortSpinner.onItemSelectedListener = object : OnSpinnerItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val spinnerPosition = binding.sortSpinner.selectedItemPosition
                viewModel.setSortedType(
                    when (spinnerPosition) {
                        SortType.SORT_BY_PRIORITY.spinnerPosition -> SortType.SORT_BY_PRIORITY
                        SortType.SORT_BY_NAME.spinnerPosition -> SortType.SORT_BY_NAME
                        else -> SortType.SORT_BY_PRIORITY
                    }
                )
            }
        }
    }

    private fun setBottomNavigationViewsSettings() {
        with(binding) {
            searchEditText.doAfterTextChanged {
                viewModel.setSearchString(it.toString())
            }
            buttonUp.setOnClickListener {
                setSortButtonColor(buttonUp, buttonDown)
                viewModel.setReversed(false)
            }
            buttonDown.setOnClickListener {
                setSortButtonColor(buttonDown, buttonUp)
                viewModel.setReversed(true)
            }
        }
    }

    private fun setSortButtonColor(redButton: ImageView, grayButton: ImageView) {
        redButton.background = ContextCompat.getDrawable(
            requireActivity(),
            R.drawable.background_sort_button_selected
        )
        grayButton.background = ContextCompat.getDrawable(
            requireActivity(),
            R.drawable.background_sort_button_not_selected
        )
    }

    private fun setInitSortButtonColor(buttonDown: ImageView, buttonUp: ImageView) {
        if (viewModel.getReversed()) {
            setSortButtonColor(redButton = buttonDown, grayButton = buttonUp)
        } else {
            setSortButtonColor(redButton = buttonUp, grayButton = buttonDown)
        }
    }
}