package com.example.habits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.habits.databinding.ActivityFirstBinding

class FirstActivity : AppCompatActivity() {

    private val viewBinding: ActivityFirstBinding by viewBinding()
    private var isIntent = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        Log.d(FIRST_ACTIVITY_TAG, "onCreate()")

        viewBinding.counterTextView.text = "0"
        viewBinding.goToSecondActivityButton.setOnClickListener { secondActivityButtonOnClick() }
    }

    override fun onStart() {
        super.onStart()
        Log.d(FIRST_ACTIVITY_TAG, "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(FIRST_ACTIVITY_TAG, "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(FIRST_ACTIVITY_TAG, "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(FIRST_ACTIVITY_TAG, "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(FIRST_ACTIVITY_TAG, "onDestroy()")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(FIRST_ACTIVITY_TAG, "onSaveInstanceState()")

        val currentNumber = viewBinding.counterTextView.text.toString().toInt()
        if (isIntent) outState.putInt(COUNTER_KEY, currentNumber) else outState.putInt(COUNTER_KEY, currentNumber + 1)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(FIRST_ACTIVITY_TAG, "onRestoreInstanceState()")

        val restoreNumber = savedInstanceState.getInt(COUNTER_KEY, 0)
        viewBinding.counterTextView.text = restoreNumber.toString()
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(FIRST_ACTIVITY_TAG, "onRestart()")
    }

    private fun secondActivityButtonOnClick() {
        val intent = Intent(this@FirstActivity, SecondActivity::class.java).apply {
            putExtra(COUNTER_KEY, viewBinding.counterTextView.text.toString())
        }
        isIntent = true
        startActivity(intent)
    }

    companion object {
        const val COUNTER_KEY = "counter key"
        private const val FIRST_ACTIVITY_TAG = "tag_first_activity"
    }
}