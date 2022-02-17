package com.example.habits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.habits.databinding.ActivityFirstBinding

class FirstActivity : AppCompatActivity() {

    private val viewBinding: ActivityFirstBinding by viewBinding()

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
        outState.putInt(COUNTER_KEY, viewBinding.counterTextView.text.toString().toInt())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val restoreNumber = savedInstanceState.getInt(COUNTER_KEY, 0) + 1
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
        startActivity(intent)
    }

    companion object {
        const val COUNTER_KEY = "counter key"
        private const val FIRST_ACTIVITY_TAG = "tag_first_activity"
    }
}