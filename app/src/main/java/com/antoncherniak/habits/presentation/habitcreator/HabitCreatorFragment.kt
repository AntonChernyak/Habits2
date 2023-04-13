package com.antoncherniak.habits.presentation.habitcreator

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.antoncherniak.habits.R
import com.antoncherniak.habits.databinding.FragmentHabitCreatorBinding
import com.antoncherniak.habits.presentation.extensions.getBackgroundColor
import com.antoncherniak.habits.presentation.extensions.hideKeyboard
import com.antoncherniak.habits.presentation.habitslist.HabitListFragment
import com.antoncherniak.habits.presentation.habitslist.HabitListFragment.Companion.REQUEST_ID_KEY
import com.antoncherniak.habits.domain.model.HabitType
import com.antoncherniak.habits.domain.model.PriorityType
import com.antoncherniak.habits.presentation.extensions.OnSpinnerItemSelectedListener
import com.antoncherniak.habits.presentation.extensions.viewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlin.math.roundToInt

class HabitCreatorFragment : Fragment() {

    private val binding: FragmentHabitCreatorBinding by viewBinding()
    private val viewModel: HabitCreatorViewModel by viewModels { viewModelFactory() }
    private val navArgs: HabitCreatorFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_habit_creator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createHabitPrioritySpinner()
        binding.saveHabitButton.setOnClickListener { saveHabitButtonClick(it) }
        if (navArgs.habitId != DEFAULT_HABIT_ID && savedInstanceState == null) {
            viewModel.getHabitById(navArgs.habitId)
            viewModel.isNewHabit = false
        } else if (savedInstanceState == null) {
            viewModel.setHabitId((13..10000).random())
            viewModel.isNewHabit = true
        }
        setDataFromViewModel()
        setColorPicker()
        setRgbString()
        setHsvString()
        setViewsChangedListeners()

        viewModel.resultHabitId.observe(viewLifecycleOwner) { resultId ->
            showSnackBar(binding.badHabitRadioButton, resultId)
            requireActivity().supportFragmentManager.setFragmentResult(
                REQUEST_ID_KEY,
                HabitListFragment.resultIdBundle(resultId.toString())
            )
        }
    }

    private fun createHabitPrioritySpinner() {
        val spinnerAdapter = ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.prioritySpinnerDataArray,
            android.R.layout.simple_spinner_item
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.prioritySpinner.adapter = spinnerAdapter
    }

    private fun setViewsChangedListeners() {
        binding.titleEditText.doAfterTextChanged {
            viewModel.setHabitTitle(it.toString())
        }

        binding.descriptionEditText.doAfterTextChanged {
            viewModel.setHabitDescription(it.toString().trim())
        }

        binding.prioritySpinner.onItemSelectedListener = object : OnSpinnerItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.setHabitPriority(
                    getTypeBySpinnerPosition(binding.prioritySpinner.selectedItem.toString())
                )
            }
        }

        binding.habitTypesRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            if (binding.badHabitRadioButton.id == checkedId) viewModel.setHabitType(HabitType.BAD_HABIT)
            if (binding.goodHabitRadioButton.id == checkedId) viewModel.setHabitType(HabitType.GOOD_HABIT)
        }

        binding.periodDaysEditText.doAfterTextChanged {
            if (it.toString().startsWith("0")) binding.periodDaysEditText.setText("")
            else viewModel.setPeriodDays(it.toString())
        }

        binding.periodTimesEditText.doAfterTextChanged {
            if (it.toString().startsWith("0")) binding.periodTimesEditText.setText("")
            else viewModel.setPeriodTimes(it.toString())
        }
    }

    private fun getTypeBySpinnerPosition(priority: String): PriorityType {
        return when (priority) {
            resources.getString(R.string.high_priority) -> PriorityType.HIGH
            resources.getString(R.string.medium_priority) -> PriorityType.MEDIUM
            else -> PriorityType.LOW
        }
    }

    private fun saveHabitButtonClick(view: View) {
        if (!fillInRequiredFields()) {
            Snackbar
                .make(view, getString(R.string.fill_in_required_fields), Snackbar.LENGTH_LONG)
                .show()
        } else {
            val habitOldId = navArgs.habitId
            viewModel.createOrUpdateHabit(habitOldId)
        }
        requireActivity().hideKeyboard()
    }

    private fun fillInRequiredFields(): Boolean {
        with(binding) {
            checkFillForEditText(periodDaysEditText)
            checkFillForEditText(periodTimesEditText)
            checkFillForEditText(titleEditText)
            return periodDaysEditText.text.isNotEmpty() &&
                    periodTimesEditText.text.isNotEmpty() &&
                    titleEditText.text.isNotEmpty()
        }
    }

    private fun checkFillForEditText(editText: EditText) {
        editText.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                requireActivity(),
                if (editText.text.isEmpty()) R.color.error_color else R.color.primary_light_color
            )
        )
    }

    private fun showSnackBar(view: View, habitId: Int = DEFAULT_HABIT_ID) {
        val message = if (viewModel.isNewHabit) {
            getString(R.string.habit_added)
        } else getString(R.string.habit_edited)

        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction(
                getString(R.string.cancel)
            ) { setActionToSnackbar(habitId) }
            .setActionTextColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.primary_light_color
                )
            )
            .show()
    }

    private fun setActionToSnackbar(habitId: Int) {
        if (viewModel.isNewHabit) {
            viewModel.removeHabit(habitId)
        } else {
            setDataFromViewModel()
            viewModel.updateHabit(viewModel.getInitHabit())
        }
    }

    private fun setDataFromViewModel() {
        viewModel.getCurrentHabit().let { habit ->
            with(binding) {
                creatorToolbar.title = getString(R.string.edited_habit)
                titleEditText.setText(habit.title)
                descriptionEditText.setText(habit.description)
                periodDaysEditText.setText(habit.periodDays)
                periodTimesEditText.setText(habit.periodTimes)
                prioritySpinner.setSelection(habit.priority.spinnerPos)
                setHabitType(habit.type)
                selectedColorView.backgroundTintList =
                    ColorStateList.valueOf(habit.color)
                selectedColorView.foreground =
                    ContextCompat.getDrawable(
                        requireActivity(),
                        R.drawable.selected_color_foreground
                    )
            }
        }
    }

    private fun setHabitType(type: HabitType) {
        if (type == HabitType.GOOD_HABIT) binding.goodHabitRadioButton.isChecked = true
        else binding.badHabitRadioButton.isChecked = true
    }

    private fun setColorPicker() {
        with(binding) {
            selectedColorView.setOnClickListener {
                colorScrollView.isVisible = !colorScrollView.isVisible
            }

            myColorPicker.listener = { color ->
                selectedColorView.backgroundTintList = color
                colorScrollView.visibility = View.GONE
                viewModel.setHabitColor(selectedColorView.getBackgroundColor())
                setRgbString()
                setHsvString()
            }
        }
    }

    private fun setRgbString() {
        with(binding) {
            val colorCode = selectedColorView.getBackgroundColor()
            rgbTextView.text = resources.getString(
                R.string.rgb_string,
                Color.red(colorCode).toString(),
                Color.green(colorCode).toString(),
                Color.blue(colorCode).toString()
            )
        }
    }

    private fun setHsvString() {
        val colorCode = binding.selectedColorView.getBackgroundColor()
        val hsvArray = FloatArray(3)
        Color.colorToHSV(colorCode, hsvArray)
        binding.hsvTextView.text = resources.getString(
            R.string.hsv_string,
            hsvArray[0].roundToInt().toString(),
            (hsvArray[1] * 100).toInt().toString(),
            (hsvArray[2] * 100).toInt().toString()
        )
    }

    companion object {
        const val DEFAULT_HABIT_ID = -1
    }
}