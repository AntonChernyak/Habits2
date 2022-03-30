package com.example.habits.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.habits.enum.HabitType
import com.example.habits.fragment.HabitsListFragment

class HabitViewPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = HabitType.values().size

    override fun createFragment(position: Int): Fragment {
        return HabitsListFragment.newInstance(HabitType.values()[position])
    }

}