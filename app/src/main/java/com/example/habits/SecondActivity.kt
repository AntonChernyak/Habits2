package com.example.habits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.habits.FirstActivity.Companion.COUNTER_KEY
import com.example.habits.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private val viewBinding: ActivitySecondBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        Log.d(SECOND_ACTIVITY_TAG, "OnCreate()")

        val number = intent.getStringExtra(COUNTER_KEY)?.toInt() ?: 0
        viewBinding.squareTextView.text = (number * number).toString()
    }

    override fun onStart() {
        super.onStart()
        Log.d(SECOND_ACTIVITY_TAG, "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(SECOND_ACTIVITY_TAG, "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(SECOND_ACTIVITY_TAG, "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(SECOND_ACTIVITY_TAG, "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(SECOND_ACTIVITY_TAG, "onDestroy()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(SECOND_ACTIVITY_TAG, "onRestart()")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(SECOND_ACTIVITY_TAG, "onSaveInstanceState()")

        outState.putString(SQUARE_KEY, viewBinding.squareTextView.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(SECOND_ACTIVITY_TAG, "onRestoreInstanceState()")

        val squareNumber = savedInstanceState.getString(SQUARE_KEY, "0")
        viewBinding.squareTextView.text = squareNumber
    }

    companion object {
        private const val SECOND_ACTIVITY_TAG = "tag_second_activity"
        private const val SQUARE_KEY = "square key"
    }
}