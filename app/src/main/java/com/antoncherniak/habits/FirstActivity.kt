package com.antoncherniak.habits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import by.kirich1409.viewbindingdelegate.viewBinding
import com.antoncherniak.habits.databinding.ActivityFirstBinding

class FirstActivity : AppCompatActivity() {

    private val binding: ActivityFirstBinding by viewBinding()
    private var isIntent = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        Log.d(FIRST_ACTIVITY_TAG, "onCreate()_1")

        if (savedInstanceState == null) {
            binding.firstActivityNumberTextView.text = DEFAULT_COUNTER_VALUE
        }

        binding.toSecondActivityButton.setOnClickListener {
            toSecondActivityButtonOnClick()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(FIRST_ACTIVITY_TAG, "onStart()_1")
        isIntent = false
    }

    override fun onResume() {
        super.onResume()
        Log.d(FIRST_ACTIVITY_TAG, "onResume()_1")
    }

    override fun onPause() {
        super.onPause()
        Log.d(FIRST_ACTIVITY_TAG, "onPause()_1")
    }

    override fun onStop() {
        super.onStop()
        Log.d(FIRST_ACTIVITY_TAG, "onStop()_1")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(FIRST_ACTIVITY_TAG, "onDestroy()_1")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(FIRST_ACTIVITY_TAG, "onSaveInstanceState()_1")

        val saveValue = binding.firstActivityNumberTextView
            .text
            .toString()
            .toInt()
        outState.putInt(COUNTER_KEY, if (isIntent) saveValue else saveValue.plus(1))
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(FIRST_ACTIVITY_TAG, "onRestoreInstanceState()_1")

        val restoreValue = savedInstanceState.getInt(COUNTER_KEY, DEFAULT_COUNTER_VALUE.toInt())
        binding.firstActivityNumberTextView.text = restoreValue.toString()
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(FIRST_ACTIVITY_TAG, "onRestart()_1")
    }

    private fun toSecondActivityButtonOnClick() {
        val intent = SecondActivity.createIntent(
            this@FirstActivity,
            binding.firstActivityNumberTextView.text.toString()
        )
        isIntent = true
        startActivity(intent)
    }

    companion object {
        private const val FIRST_ACTIVITY_TAG = "first activity"
        private const val COUNTER_KEY = "key counter"
        private const val DEFAULT_COUNTER_VALUE = "0"
    }
}