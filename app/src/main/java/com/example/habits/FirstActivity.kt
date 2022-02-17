package com.example.habits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.habits.databinding.ActivityMainBinding

class FirstActivity : AppCompatActivity() {

    private val viewBinding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewBinding.counterTextView.text = "0"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(COUNTER_KEY, viewBinding.counterTextView.text.toString().toInt())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val restoreNumber = savedInstanceState.getInt(COUNTER_KEY, 0) + 1
        viewBinding.counterTextView.text = restoreNumber.toString()
    }

    companion object {
        private const val COUNTER_KEY = "counter key"
    }
}