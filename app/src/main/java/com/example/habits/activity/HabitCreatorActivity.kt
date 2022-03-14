package com.example.habits.activity

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.habits.R
import com.example.habits.activity.HabitsListActivity.Companion.HABIT_EXTRA_KEY
import com.example.habits.activity.HabitsListActivity.Companion.POSITION_KEY
import com.example.habits.databinding.ActivityHabitCreatorBinding
import com.example.habits.enum.HabitType
import com.example.habits.extension.getBackgroundColor
import com.example.habits.extension.hideKeyboard
import com.example.habits.model.HabitItem
import com.example.habits.repository.MockRepository
import com.google.android.material.snackbar.Snackbar

class HabitCreatorActivity : AppCompatActivity() {

    private val binding: ActivityHabitCreatorBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_creator)

        createHabitPrioritySpinner()
        binding.createHabitButton.setOnClickListener { createHabitButtonClick(it) }
        setDataFromIntent()
    }

    private fun createHabitPrioritySpinner() {
        val spinnerAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.prioritySpinnerDataArray,
            android.R.layout.simple_spinner_item
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.prioritySpinner.adapter = spinnerAdapter
    }

    private fun createHabit(): HabitItem {
        return HabitItem(
            title = binding.habitTitleEditText.text.toString(),
            description = binding.habitDescriptionEditText.text.toString(),
            priority = binding.prioritySpinner.selectedItem.toString(),
            type = getHabitType(),
            periodCount = binding.periodTimesEditText.text.toString(),
            periodDays = binding.periodDaysEditText.text.toString(),
            color = binding.selectedColorView.getBackgroundColor()
        )
    }

    private fun createHabitButtonClick(view: View) {
        if (binding.habitTitleEditText.text.isNullOrEmpty() ||
            binding.periodDaysEditText.text.isNullOrEmpty() ||
            binding.periodTimesEditText.text.isNullOrEmpty()
        ) {
            fillInRequiredFields(view)
        } else {
            allRequiredDataEntered()
            val position = intent.getIntExtra(POSITION_KEY, DEFAULT_POSITION)
            val habit = createHabit()

            if (position == DEFAULT_POSITION) {
                MockRepository.addHabit(habit = habit)
                showCreateSnackbar(view)
            } else {
                replaceHabit(habit, position)
                showEditSnackBar(view, position)
            }
        }

        this.hideKeyboard()
    }

    private fun fillInRequiredFields(view: View) {
        Snackbar.make(view, getString(R.string.fill_in_required_fields), Snackbar.LENGTH_LONG)
            .show()
        if (binding.periodTimesEditText.text.isNullOrEmpty()) {
            binding.periodTimesEditText.backgroundTintList = ColorStateList.valueOf(Color.RED)
        } else {
            binding.periodTimesEditText.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.primary_color_green))
        }

        if (binding.periodDaysEditText.text.isNullOrEmpty()) {
            binding.periodDaysEditText.backgroundTintList = ColorStateList.valueOf(Color.RED)
        } else {
            binding.periodDaysEditText.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.primary_color_green))
        }

        if (binding.habitTitleEditText.text.isNullOrEmpty()) {
            binding.habitTitleEditText.backgroundTintList = ColorStateList.valueOf(Color.RED)
        } else {
            binding.habitTitleEditText.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.primary_color_green))
        }
    }

    private fun allRequiredDataEntered() {
        binding.habitTitleEditText.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.primary_color_green))
        binding.periodDaysEditText.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.primary_color_green))
        binding.periodTimesEditText.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.primary_color_green))
    }

    private fun getHabitType(): HabitType {
        val checkId = binding.habitTypesRadioGroup.checkedRadioButtonId
        return if (checkId == binding.goodHabitRadioButton.id) HabitType.GOOD_HABIT
        else HabitType.BAD_HABIT
    }

    private fun showCreateSnackbar(view: View) {
        Snackbar.make(view, getString(R.string.habit_added), Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.cancel)) {
                MockRepository.removeLastHabit()
            }
            .setActionTextColor(ContextCompat.getColor(this, R.color.primary_color_green))
            .show()
    }

    private fun showEditSnackBar(view: View, position: Int) {
        Snackbar.make(view, getString(R.string.habit_edited), Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.cancel)) {
                setDataFromIntent()
                val editingHabit = intent.getParcelableExtra<HabitItem>(HABIT_EXTRA_KEY)
                editingHabit?.let { habit -> replaceHabit(habit, position) }
            }
            .setActionTextColor(ContextCompat.getColor(this, R.color.primary_color_green))
            .show()
    }

    private fun setDataFromIntent() {
        val editingHabit = intent.getParcelableExtra<HabitItem>(HABIT_EXTRA_KEY)
        if (editingHabit != null) {
            binding.habitTitleEditText.setText(editingHabit.title)
            binding.habitDescriptionEditText.setText(editingHabit.description)
            binding.periodDaysEditText.setText(editingHabit.periodDays)
            binding.periodTimesEditText.setText(editingHabit.periodCount)
            binding.prioritySpinner.setSelection(editingHabit.priority.toInt() - 1)
            setHabitType(editingHabit.type)
            binding.selectedColorView.backgroundTintList =
                ColorStateList.valueOf(editingHabit.color)
        }
    }

    private fun setHabitType(type: HabitType) {
        if (type == HabitType.GOOD_HABIT) binding.goodHabitRadioButton.isChecked = true
        else binding.badHabitRadioButton.isChecked = true
    }

    private fun replaceHabit(habit: HabitItem, position: Int) {
        MockRepository.removeHabit(MockRepository.getHabits()[position])
        MockRepository.addHabit(position, habit)
    }

    companion object {
        const val DEFAULT_POSITION = -1
    }
}