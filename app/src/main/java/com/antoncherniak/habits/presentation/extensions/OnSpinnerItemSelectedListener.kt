package com.antoncherniak.habits.presentation.extensions

import android.widget.AdapterView

interface OnSpinnerItemSelectedListener: AdapterView.OnItemSelectedListener {

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}