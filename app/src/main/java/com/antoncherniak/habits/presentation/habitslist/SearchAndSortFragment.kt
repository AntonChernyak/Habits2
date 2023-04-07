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
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.antoncherniak.habits.R
import com.antoncherniak.habits.data.repository.MockRepository.getHabits
import com.antoncherniak.habits.databinding.FragmentSearchAndSortBinding
import com.antoncherniak.habits.presentation.extensions.viewModelFactory

class SearchAndSortFragment : Fragment() {

    private val viewModel: HabitListViewModel by viewModels { viewModelFactory() }
    private val binding: FragmentSearchAndSortBinding by viewBinding()
    private var reversed = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_and_sort, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        binding.sortSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    getHabits()
                    binding.habitRecyclerView.post {
                        binding.habitRecyclerView.layoutManager?.scrollToPosition(0)
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
    }

    private fun setBottomNavigationViewsSettings() {
        with(binding) {
            setInitSortButtonColor(buttonDown, buttonUp)
            searchEditText.doAfterTextChanged {
                getHabits()
            }
            buttonUp.setOnClickListener {
                setSortButtonColor(buttonUp, buttonDown, false)
            }
            buttonDown.setOnClickListener {
                setSortButtonColor(buttonDown, buttonUp, true)
            }
        }
    }

    private fun setSortButtonColor(redButton: ImageView, grayButton: ImageView, reverse: Boolean) {
        redButton.background = ContextCompat.getDrawable(
            requireActivity(),
            R.drawable.background_sort_button_selected
        )
        grayButton.background = ContextCompat.getDrawable(
            requireActivity(),
            R.drawable.background_sort_button_not_selected
        )
        reversed = reverse
        getHabits()
        binding.habitRecyclerView.post {
            binding.habitRecyclerView.layoutManager?.scrollToPosition(0)
        }
    }

    private fun setInitSortButtonColor(buttonDown: ImageView, buttonUp: ImageView) {
        if (reversed) {
            buttonDown.background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.background_sort_button_selected
            )
        } else buttonUp.background = ContextCompat.getDrawable(
            requireActivity(),
            R.drawable.background_sort_button_selected
        )
    }
}