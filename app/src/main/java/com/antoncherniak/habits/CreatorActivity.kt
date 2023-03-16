package com.antoncherniak.habits

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.antoncherniak.habits.customview.ColorPicker
import com.antoncherniak.habits.databinding.ActivityCreatorBinding
import com.antoncherniak.habits.extensions.getBackgroundColor
import com.antoncherniak.habits.extensions.hideKeyboard
import com.antoncherniak.habits.model.Habit
import com.antoncherniak.habits.model.HabitType
import com.antoncherniak.habits.model.PriorityType
import com.antoncherniak.habits.repository.MockRepository
import com.google.android.material.snackbar.Snackbar
import kotlin.math.roundToInt

class CreatorActivity : AppCompatActivity() {

    private val binding: ActivityCreatorBinding by viewBinding()
    private val habitsRepository: MockRepository by lazy {
        (applicationContext as App).habitRepository
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creator)

        createHabitPrioritySpinner()
        binding.saveHabitButton.setOnClickListener { saveHabitButtonClick(it) }
        setDataFromIntent()
        setColorPicker()
        setRgbString()
        setHsvString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            putString(TITLE_KEY, binding.titleEditText.text.toString())
            putString(DESCRIPTION_KEY, binding.descriptionEditText.text.toString())
            putString(PRIORITY_KEY, binding.prioritySpinner.selectedItem.toString())
            putString(PERIOD_COUNT_KEY, binding.periodTimesEditText.text.toString())
            putString(PERIOD_DAYS_KEY, binding.periodDaysEditText.text.toString())
            putString(TYPE_KEY, getHabitType().name)
            putInt(COLOR_KEY, binding.selectedColorView.getBackgroundColor())
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.apply {
            binding.titleEditText.setText(this.getString(TITLE_KEY))
            binding.descriptionEditText.setText(this.getString(DESCRIPTION_KEY))
            binding.periodDaysEditText.setText(this.getString(PERIOD_DAYS_KEY))
            binding.periodTimesEditText.setText(this.getString(PERIOD_COUNT_KEY))
            this.getString(PRIORITY_KEY)?.toInt()
                ?.minus(1)?.let { binding.prioritySpinner.setSelection(it) }
            when (this.getString(TYPE_KEY)) {
                HabitType.GOOD_HABIT.name -> setHabitType(HabitType.GOOD_HABIT)
                HabitType.BAD_HABIT.name -> setHabitType(HabitType.BAD_HABIT)
            }
            binding.selectedColorView.backgroundTintList =
                ColorStateList.valueOf(savedInstanceState.getInt(COLOR_KEY, Color.WHITE))
        }
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

    private fun createHabit(): Habit {
        return Habit(
            id = (13..10000).random(),
            title = binding.titleEditText.text.toString(),
            description = binding.descriptionEditText.text.toString(),
            priority = getTypeBySpinnerPosition(binding.prioritySpinner.selectedItem.toString()),
            type = getHabitType(),
            periodTimes = binding.periodTimesEditText.text.toString(),
            periodDays = binding.periodDaysEditText.text.toString(),
            color = binding.selectedColorView.getBackgroundColor()
        )
    }
    private fun getTypeBySpinnerPosition(pos: String) : PriorityType {
        return when(pos) {
            resources.getString(R.string.high_priority) -> PriorityType.HIGH
            resources.getString(R.string.medium_priority) -> PriorityType.MEDIUM
            else -> PriorityType.LOW
        }
    }

    private fun saveHabitButtonClick(view: View) {
        if (binding.titleEditText.text.isNullOrEmpty() ||
            binding.periodDaysEditText.text.isNullOrEmpty() ||
            binding.periodTimesEditText.text.isNullOrEmpty()
        ) {
            fillInRequiredFields(view)
        } else {
            allRequiredDataEntered()
            val position = intent.getIntExtra(POSITION_KEY, DEFAULT_POSITION)
            val habit = createHabit()
            var resultId = 0

            if (position == DEFAULT_POSITION) {
                habitsRepository.addHabit(habit = habit)
                resultId = habit.id
                showCreateSnackbar(view)
            } else {
                val oldId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableExtra(HABIT_EXTRA_KEY, Habit::class.java)?.id ?: -1
                } else {
                    intent.getParcelableExtra<Habit>(HABIT_EXTRA_KEY)?.id ?: -1
                }
                resultId = oldId
                replaceHabit(habit.copy(id = oldId))
                showEditSnackBar(view)
            }
            val data = Intent().putExtra(ID_RESULT_KEY, resultId.toString())
            setResult(Activity.RESULT_OK, data)
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
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.primary_light_color))
        }

        if (binding.periodDaysEditText.text.isNullOrEmpty()) {
            binding.periodDaysEditText.backgroundTintList = ColorStateList.valueOf(Color.RED)
        } else {
            binding.periodDaysEditText.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.primary_light_color))
        }

        if (binding.titleEditText.text.isNullOrEmpty()) {
            binding.titleEditText.backgroundTintList = ColorStateList.valueOf(Color.RED)
        } else {
            binding.titleEditText.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.primary_light_color))
        }
    }

    private fun allRequiredDataEntered() {
        binding.titleEditText.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.primary_light_color))
        binding.periodDaysEditText.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.primary_light_color))
        binding.periodTimesEditText.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.primary_light_color))
    }

    private fun showCreateSnackbar(view: View) {
        Snackbar.make(view, getString(R.string.habit_added), Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.cancel)) {
                habitsRepository.removeLastHabit()
            }
            .setActionTextColor(ContextCompat.getColor(this, R.color.primary_light_color))
            .show()
    }

    private fun showEditSnackBar(view: View) {
        Snackbar.make(view, getString(R.string.habit_edited), Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.cancel)) {
                setDataFromIntent()
                val editingHabit = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableExtra(HABIT_EXTRA_KEY, Habit::class.java)
                } else {
                    intent.getParcelableExtra(HABIT_EXTRA_KEY)
                }
                editingHabit?.let { habit -> replaceHabit(habit) }
            }
            .setActionTextColor(ContextCompat.getColor(this, R.color.primary_light_color))
            .show()
    }

    private fun setDataFromIntent() {
        val editingHabit = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(HABIT_EXTRA_KEY, Habit::class.java)
        } else {
            intent.getParcelableExtra(HABIT_EXTRA_KEY)
        }
        if (editingHabit != null) {
            binding.creatorToolbar.title = getString(R.string.edited_habit)
            binding.titleEditText.setText(editingHabit.title)
            binding.descriptionEditText.setText(editingHabit.description)
            binding.periodDaysEditText.setText(editingHabit.periodDays)
            binding.periodTimesEditText.setText(editingHabit.periodTimes)
            binding.prioritySpinner.setSelection(editingHabit.priority.spinnerPos)
            setHabitType(editingHabit.type)
            binding.selectedColorView.backgroundTintList =
                ColorStateList.valueOf(editingHabit.color)
            binding.selectedColorView.foreground = ContextCompat.getDrawable(this, R.drawable.selected_color_foreground)
        }
    }

    private fun setHabitType(type: HabitType) {
        if (type == HabitType.GOOD_HABIT) binding.goodHabitRadioButton.isChecked = true
        else binding.badHabitRadioButton.isChecked = true
    }

    private fun replaceHabit(habit: Habit) {
        habitsRepository.replaceHabit(habit)
    }

    private fun getHabitType(): HabitType {
        val checkId = binding.habitTypesRadioGroup.checkedRadioButtonId
        return if (checkId == binding.goodHabitRadioButton.id) HabitType.GOOD_HABIT
        else HabitType.BAD_HABIT
    }

    private fun setColorPicker() {
        binding.selectedColorView.setOnClickListener {
            val scrollViewVisibility = binding.colorScrollView.scrollView.visibility
            binding.colorScrollView.scrollView.visibility =
                if (scrollViewVisibility == View.GONE) View.VISIBLE
                else View.GONE
        }

        binding.colorScrollView.colorPickerContainer.background = BitmapDrawable(
            resources,
            ColorPicker.createBackgroundBitmap(this)
        )

        ColorPicker.createColorPickerItems(this) {
            val color = it.backgroundTintList
            binding.selectedColorView.backgroundTintList = color
            binding.colorScrollView.scrollView.visibility = View.GONE
            setRgbString()
            setHsvString()
        }
    }

    private fun setRgbString() {
        val colorCode = binding.selectedColorView.getBackgroundColor()
        binding.rgbTextView.text = resources.getString(
            R.string.rgb_string,
            Color.red(colorCode).toString(),
            Color.green(colorCode).toString(),
            Color.blue(colorCode).toString()
        )
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
        const val DEFAULT_POSITION = -1
        const val TITLE_KEY = "title_key"
        const val DESCRIPTION_KEY = "description_key"
        const val PERIOD_COUNT_KEY = "period_count_key"
        const val PERIOD_DAYS_KEY = "period_days_key"
        const val TYPE_KEY = "type_key"
        const val PRIORITY_KEY = "priority_key"
        const val POSITION_KEY = "position_key"
        const val COLOR_KEY = "color_key"
        const val ID_RESULT_KEY = "id_res_key"

        const val HABIT_EXTRA_KEY = "habit_extra_key"

        fun newIntent(
            context: Context,
            habit: Habit? = null,
            position: Int = DEFAULT_POSITION
        ): Intent {
            val intent = Intent(context, CreatorActivity::class.java).apply {
                putExtra(POSITION_KEY, position)
                putExtra(HABIT_EXTRA_KEY, habit)
            }
            return intent
        }
    }
}