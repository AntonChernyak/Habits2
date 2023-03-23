package com.antoncherniak.habits.habitslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.antoncherniak.habits.R
import com.antoncherniak.habits.databinding.FragmentHabitListViewPagerContainerBinding
import com.antoncherniak.habits.habitslist.adapter.viewpager.HabitViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator


class HabitListViewPagerContainerFragment : Fragment() {

    private val binding: FragmentHabitListViewPagerContainerBinding by viewBinding()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_habit_list_view_pager_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.habitsViewPager.apply {
            adapter = HabitViewPagerAdapter(this@HabitListViewPagerContainerFragment)
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            val child = getChildAt(0)
            if (child is RecyclerView) {
                child.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            }
        }

        TabLayoutMediator(binding.habitsTabLayout, binding.habitsViewPager) { tab, position ->
            when (position) {
                POSITIVE_TAB_POSITION -> tab.text = getString(R.string.goods)
                NEGATIVE_TAB_POSITION -> tab.text = getString(R.string.bads)
            }
        }.attach()
    }

    companion object {
        const val POSITIVE_TAB_POSITION = 0
        const val NEGATIVE_TAB_POSITION = 1
    }
}