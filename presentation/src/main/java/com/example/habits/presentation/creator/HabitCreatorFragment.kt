package com.example.habits.presentation.creator

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.habits.App
import com.example.habits.R
import com.example.habits.presentation.colorpicker.ColorPicker
import com.example.habits.presentation.extension.getBackgroundColor
import com.example.habits.data.database.model_vo.HabitType
import com.example.habits.presentation.extension.hideKeyboard
import com.example.habits.data.database.model_vo.HabitItem
import com.example.habits.data.database.model_vo.PriorityType
import com.example.habits.databinding.FragmentHabitCreatorBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.serialization.ExperimentalSerializationApi
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt

@ExperimentalSerializationApi
class HabitCreatorFragment : Fragment() {

    private val binding: FragmentHabitCreatorBinding by viewBinding()

    @Inject
    lateinit var habitCreatorViewModel: HabitCreatorViewModel

    private val greenColor by lazy {
        ColorStateList.valueOf(
            ContextCompat.getColor(
                requireActivity(),
                R.color.primary_color_green
            )
        )
    }
    private val redColor = ColorStateList.valueOf(Color.RED)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).component.fragmentViewModelComponentBuilder()
            .fragment(this)
            .build().inject(this
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_habit_creator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createHabitPrioritySpinner()
        binding.createHabitButton.setOnClickListener { createHabitButtonClick(it) }
        setDataFromArguments()
        setColorPicker()
        setRgbString()
        setHsvString()
        savedInstanceState?.let { onRestoreInstanceState(it) }

        binding.habitsCreatorToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.habitsCreatorToolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            putString(TITLE_KEY, binding.habitTitleEditText.text.toString())
            putString(DESCRIPTION_KEY, binding.habitDescriptionEditText.text.toString())
            putString(PRIORITY_KEY, binding.prioritySpinner.selectedItem.toString())
            putString(PERIOD_COUNT_KEY, binding.periodTimesEditText.text.toString())
            putString(PERIOD_DAYS_KEY, binding.periodDaysEditText.text.toString())
            putString(TYPE_KEY, getHabitType().name)
            putInt(COLOR_KEY, binding.selectedColorView.getBackgroundColor())
        }
    }

    private fun onRestoreInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.apply {
            binding.habitTitleEditText.setText(this.getString(TITLE_KEY))
            binding.habitDescriptionEditText.setText(this.getString(DESCRIPTION_KEY))
            binding.periodDaysEditText.setText(this.getString(PERIOD_DAYS_KEY))
            binding.periodTimesEditText.setText(this.getString(PERIOD_COUNT_KEY))
            this.getString(PRIORITY_KEY)?.toInt()
                ?.minus(1)?.let { binding.prioritySpinner.setSelection(it) }
            when (this.getString(TYPE_KEY)) {
                HabitType.GOOD_HABIT.name -> setHabitType(HabitType.GOOD_HABIT)
                HabitType.BAD_HABIT.name -> setHabitType(HabitType.BAD_HABIT)
            }
            binding.selectedColorView.backgroundTintList =
                ColorStateList.valueOf(savedInstanceState.getInt(COLOR_KEY, Color.MAGENTA))
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

    private fun createHabit(): HabitItem {
        return HabitItem(
            id = "",
            title = binding.habitTitleEditText.text.toString(),
            description = binding.habitDescriptionEditText.text.toString(),
            priority = PriorityType.values()[binding.prioritySpinner.selectedItemPosition],
            type = getHabitType(),
            periodCount = binding.periodTimesEditText.text.toString(),
            periodDays = binding.periodDaysEditText.text.toString(),
            color = binding.selectedColorView.getBackgroundColor(),
            dateOfCreation = (Date().time / DAY_TO_MILLISECONDS).toInt()
        )
    }

    private fun createHabitButtonClick(view: View) {
        if (binding.habitTitleEditText.text.isNullOrEmpty() ||
            binding.habitDescriptionEditText.text.isNullOrEmpty() ||
            binding.periodDaysEditText.text.isNullOrEmpty() ||
            binding.periodTimesEditText.text.isNullOrEmpty()
        ) {
            fillInRequiredFields(view)
        } else {
            allRequiredDataEntered()
            val position = arguments?.getInt(POSITION_KEY) ?: DEFAULT_POSITION
            val habit = createHabit()

            if (position == DEFAULT_POSITION) {
                habitCreatorViewModel.addHabit(habit)
                showCreateSnackbar(view, habit)
            } else {
                val oldId = requireArguments().getParcelable<HabitItem>(HABIT_EXTRA_KEY)?.id ?: -1
                replaceHabit(habit.copy(id = oldId.toString()))
                showEditSnackBar(view)
            }
        }

        requireActivity().hideKeyboard()
    }

    private fun fillInRequiredFields(view: View) {
        Snackbar.make(view, getString(R.string.fill_in_required_fields), Snackbar.LENGTH_LONG)
            .show()

        if (binding.periodTimesEditText.text.isNullOrEmpty()) {
            binding.periodTimesEditText.backgroundTintList = redColor
        } else {
            binding.periodTimesEditText.backgroundTintList = greenColor
        }

        if (binding.periodDaysEditText.text.isNullOrEmpty()) {
            binding.periodDaysEditText.backgroundTintList = redColor
        } else {
            binding.periodDaysEditText.backgroundTintList = greenColor
        }

        if (binding.habitTitleEditText.text.isNullOrEmpty()) {
            binding.habitTitleEditText.backgroundTintList = redColor
        } else {
            binding.habitTitleEditText.backgroundTintList = greenColor
        }
        if (binding.habitDescriptionEditText.text.isNullOrEmpty()) {
            binding.habitDescriptionEditText.backgroundTintList = redColor
        } else {
            binding.habitDescriptionEditText.backgroundTintList = greenColor
        }
    }

    private fun allRequiredDataEntered() {
        binding.habitTitleEditText.backgroundTintList = greenColor
        binding.periodDaysEditText.backgroundTintList = greenColor
        binding.periodTimesEditText.backgroundTintList = greenColor
    }

    private fun showCreateSnackbar(view: View, habit: HabitItem) {
        Snackbar.make(view, getString(R.string.habit_added), Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.cancel)) {
                habitCreatorViewModel.removeHabit(habit)
            }
            .setActionTextColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.primary_color_green
                )
            )
            .show()
    }

    private fun showEditSnackBar(view: View) {
        Snackbar.make(view, getString(R.string.habit_edited), Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.cancel)) {
                setDataFromArguments()
                val editingHabit = requireArguments().getParcelable<HabitItem>(HABIT_EXTRA_KEY)
                editingHabit?.let { habit -> replaceHabit(habit) }
            }
            .setActionTextColor(greenColor)
            .show()
    }

    private fun setDataFromArguments() {
        val editingHabit = arguments?.getParcelable<HabitItem>(HABIT_EXTRA_KEY)
        if (editingHabit != null) {
            binding.habitTitleEditText.setText(editingHabit.title)
            binding.habitDescriptionEditText.setText(editingHabit.description)
            binding.periodDaysEditText.setText(editingHabit.periodDays)
            binding.periodTimesEditText.setText(editingHabit.periodCount)
            binding.prioritySpinner.setSelection(editingHabit.priority.ordinal)
            setHabitType(editingHabit.type)
            binding.selectedColorView.backgroundTintList =
                ColorStateList.valueOf(editingHabit.color)
        }
    }

    private fun setHabitType(type: HabitType) {
        if (type == HabitType.GOOD_HABIT) binding.goodHabitRadioButton.isChecked = true
        else binding.badHabitRadioButton.isChecked = true
    }

    private fun replaceHabit(habit: HabitItem) {
        habitCreatorViewModel.replaceHabit(habit)
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
            ColorPicker.createBackgroundBitmap(requireActivity())
        )

        ColorPicker.createColorPickerItems(requireActivity()) {
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
        private const val DAY_TO_MILLISECONDS = 1000 * 60 * 60 * 24

        const val DEFAULT_POSITION = -1
        const val TITLE_KEY = "title_key"
        const val DESCRIPTION_KEY = "description_key"
        const val PERIOD_COUNT_KEY = "period_count_key"
        const val PERIOD_DAYS_KEY = "period_days_key"
        const val TYPE_KEY = "type_key"
        const val PRIORITY_KEY = "priority_key"
        const val POSITION_KEY = "position_key"
        const val COLOR_KEY = "color_key"

        const val HABIT_EXTRA_KEY = "habit_extra_key"

    }
}