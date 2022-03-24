package com.example.habits.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.habits.App
import com.example.habits.enum.HabitType
import com.example.habits.fragment.HabitsListFragment
import com.example.habits.model.HabitItem

class HabitViewPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

/*    private val habitRepository = (activity.application as App).habitRepository

    private val positiveHabits: List<HabitItem> by lazy {
        habitRepository.getHabits().filter { it.type == HabitType.GOOD_HABIT }
    }

    private val negativeHabits: List<HabitItem> by lazy {
        habitRepository.getHabits().filter { it.type == HabitType.BAD_HABIT }
    }*/

    override fun getItemCount(): Int = TAB_COUNT

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            POSITIVE_FRAGMENT_POSITION -> HabitsListFragment.newInstance(HabitType.GOOD_HABIT)
            NEGATIVE_FRAGMENT_POSITION -> HabitsListFragment.newInstance(HabitType.BAD_HABIT)
            else -> HabitsListFragment.newInstance(HabitType.GOOD_HABIT)
        }
    }

    companion object {
        private const val TAB_COUNT = 2
        private const val POSITIVE_FRAGMENT_POSITION = 0
        private const val NEGATIVE_FRAGMENT_POSITION = 1
    }
}