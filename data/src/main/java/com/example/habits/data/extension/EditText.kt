package com.example.habits.data.extension

import android.text.Editable
import android.widget.EditText

fun EditText.afterTextChanged(action: (s: Editable?) -> Unit) =
    addTextChangedListener(afterTextChanged = action)