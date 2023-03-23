package com.antoncherniak.habits.applicationinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.antoncherniak.habits.R
import com.antoncherniak.habits.extensions.addToggleToNavigationDrawer

class ApplicationInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_application_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().addToggleToNavigationDrawer(
            R.id.drawer_layout,
            R.id.info_toolbar,
            R.string.navigation_open,
            R.string.navigation_close
        )
    }
}