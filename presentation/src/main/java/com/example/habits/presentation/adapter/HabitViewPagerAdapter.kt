package com.example.habits.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.habits.data.model_vo.HabitType
import com.example.habits.presentation.list.HabitsListFragment

class HabitViewPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = HabitType.values().size

    override fun createFragment(position: Int): Fragment {
        return HabitsListFragment.newInstance(HabitType.values()[position])
    }

}