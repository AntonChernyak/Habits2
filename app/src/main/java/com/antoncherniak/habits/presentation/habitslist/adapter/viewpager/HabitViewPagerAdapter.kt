package com.antoncherniak.habits.presentation.habitslist.adapter.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.antoncherniak.habits.presentation.habitslist.HabitListFragment
import com.antoncherniak.habits.domain.model.HabitType

class HabitViewPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = HabitType.values().size

    override fun createFragment(position: Int): Fragment {
        return HabitListFragment.newInstance(HabitType.values()[position].name)
    }

}