package com.antoncherniak.habits.presentation.habitcreator

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.antoncherniak.habits.R
import com.antoncherniak.habits.databinding.FragmentHabitCreatorBinding
import com.antoncherniak.habits.presentation.extensions.getBackgroundColor
import com.antoncherniak.habits.presentation.extensions.hideKeyboard
import com.antoncherniak.habits.presentation.habitslist.HabitListFragment
import com.antoncherniak.habits.presentation.habitslist.HabitListFragment.Companion.REQUEST_ID_KEY
import com.antoncherniak.habits.domain.model.HabitModel
import com.antoncherniak.habits.domain.model.HabitType
import com.antoncherniak.habits.domain.model.PriorityType
import com.antoncherniak.habits.presentation.extensions.viewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlin.math.roundToInt

class HabitCreatorFragment : Fragment() {

    private val binding: FragmentHabitCreatorBinding by viewBinding()
    private val viewModel: HabitCreatorViewModel by viewModels { viewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_habit_creator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onRestoreInstanceState(savedInstanceState)
        createHabitPrioritySpinner()
        binding.saveHabitButton.setOnClickListener { saveHabitButtonClick(it) }
        setDataFromIntent()
        setColorPicker()
        setRgbString()
        setHsvString()

        viewModel.resultHabitId.observe(viewLifecycleOwner) { resultId ->
            showSnackBar(binding.badHabitRadioButton, resultId)
            val data = HabitListFragment.resultIdBundle(resultId.toString())
            requireActivity().supportFragmentManager.setFragmentResult(REQUEST_ID_KEY, data)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        with(binding) {
            outState.apply {
                putString(TITLE_KEY, titleEditText.text.toString())
                putString(DESCRIPTION_KEY, descriptionEditText.text.toString())
                putString(PRIORITY_KEY, prioritySpinner.selectedItem.toString())
                putString(PERIOD_COUNT_KEY, periodTimesEditText.text.toString())
                putString(PERIOD_DAYS_KEY, periodDaysEditText.text.toString())
                putString(TYPE_KEY, getHabitType().name)
                putInt(COLOR_KEY, selectedColorView.getBackgroundColor())
            }
        }
    }

    private fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.apply {
            with(binding) {
                titleEditText.setText(getString(TITLE_KEY))
                descriptionEditText.setText(getString(DESCRIPTION_KEY))
                periodDaysEditText.setText(this@apply.getString(PERIOD_DAYS_KEY))
                periodTimesEditText.setText(this@apply.getString(PERIOD_COUNT_KEY))
                this@apply.getString(PRIORITY_KEY)?.toInt()
                    ?.minus(1)?.let { prioritySpinner.setSelection(it) }
                when (this@apply.getString(TYPE_KEY)) {
                    HabitType.GOOD_HABIT.name -> setHabitType(HabitType.GOOD_HABIT)
                    HabitType.BAD_HABIT.name -> setHabitType(HabitType.BAD_HABIT)
                }
                selectedColorView.backgroundTintList =
                    ColorStateList.valueOf(savedInstanceState.getInt(COLOR_KEY, Color.WHITE))
            }
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

    private fun createHabit(): HabitModel {
        return with(binding) {
            HabitModel(
                id = (13..10000).random(),
                title = titleEditText.text.toString(),
                description = descriptionEditText.text.toString(),
                priority = getTypeBySpinnerPosition(prioritySpinner.selectedItem.toString()),
                type = getHabitType(),
                periodTimes = periodTimesEditText.text.toString(),
                periodDays = periodDaysEditText.text.toString(),
                color = selectedColorView.getBackgroundColor()
            )
        }
    }

    private fun getTypeBySpinnerPosition(pos: String): PriorityType {
        return when (pos) {
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
            val habitOldId = arguments?.getInt(ID_KEY) ?: DEFAULT_ID
            viewModel.createOrUpdateHabit(habitOldId, createHabit())
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

    private fun showSnackBar(view: View, habitId: Int = DEFAULT_ID) {
        Log.e("TAGGG", "habitId = ${habitId}")
        val message = if (habitId == DEFAULT_ID) {
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
        if (habitId == DEFAULT_ID) {
            viewModel.removeHabit(habitId)
        } else {
            setDataFromIntent()
            val editingHabit = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments?.getParcelable(HABIT_EXTRA_KEY, HabitModel::class.java)
            } else {
                arguments?.getParcelable(HABIT_EXTRA_KEY)
            }
            editingHabit?.let { habit -> updateHabit(habit) }
        }
    }


    private fun setDataFromIntent() {
        val editingHabit = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(HABIT_EXTRA_KEY, HabitModel::class.java)
        } else {
            arguments?.getParcelable(HABIT_EXTRA_KEY)
        }
        if (editingHabit != null) {
            with(binding) {
                creatorToolbar.title = getString(R.string.edited_habit)
                titleEditText.setText(editingHabit.title)
                descriptionEditText.setText(editingHabit.description)
                periodDaysEditText.setText(editingHabit.periodDays)
                periodTimesEditText.setText(editingHabit.periodTimes)
                prioritySpinner.setSelection(editingHabit.priority.spinnerPos)
                setHabitType(editingHabit.type)
                selectedColorView.backgroundTintList =
                    ColorStateList.valueOf(editingHabit.color)
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

    private fun updateHabit(habit: HabitModel) {
        viewModel.updateHabit(habit)
    }

    private fun getHabitType(): HabitType {
        val checkId = binding.habitTypesRadioGroup.checkedRadioButtonId
        return if (checkId == binding.goodHabitRadioButton.id) HabitType.GOOD_HABIT
        else HabitType.BAD_HABIT
    }

    private fun setColorPicker() {
        with(binding) {
            selectedColorView.setOnClickListener {
                colorScrollView.isVisible = !colorScrollView.isVisible
            }

            myColorPicker.listener = { color ->
                selectedColorView.backgroundTintList = color
                colorScrollView.visibility = View.GONE
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
        const val DEFAULT_ID = -1
        const val TITLE_KEY = "title_key"
        const val DESCRIPTION_KEY = "description_key"
        const val PERIOD_COUNT_KEY = "period_count_key"
        const val PERIOD_DAYS_KEY = "period_days_key"
        const val TYPE_KEY = "type_key"
        const val PRIORITY_KEY = "priority_key"
        const val ID_KEY = "id_key"
        const val COLOR_KEY = "color_key"

        const val HABIT_EXTRA_KEY = "habit_extra_key"

        fun newBundle(
            habit: HabitModel? = null,
            id: Int = DEFAULT_ID
        ): Bundle {
            return Bundle().apply {
                putInt(ID_KEY, id)
                putParcelable(HABIT_EXTRA_KEY, habit)
            }
        }
    }

}