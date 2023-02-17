package com.antoncherniak.habits

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import by.kirich1409.viewbindingdelegate.viewBinding
import com.antoncherniak.habits.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private val binding: ActivitySecondBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        Log.d(SECOND_ACTIVITY_TAG, "onCreate()_2")

        val number = intent.getStringExtra(NUMBER_KEY)?.toInt() ?: 0
        binding.secondActivityNumberTextView.text = (number * number).toString()
    }

    override fun onStart() {
        super.onStart()
        Log.d(SECOND_ACTIVITY_TAG, "onStart()_2")
    }

    override fun onResume() {
        super.onResume()
        Log.d(SECOND_ACTIVITY_TAG, "onResume()_2")
    }

    override fun onPause() {
        super.onPause()
        Log.d(SECOND_ACTIVITY_TAG, "onPause()_2")
    }

    override fun onStop() {
        super.onStop()
        Log.d(SECOND_ACTIVITY_TAG, "onStop()_2")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(SECOND_ACTIVITY_TAG, "onDestroy()_2")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(SECOND_ACTIVITY_TAG, "onSaveInstanceState()_2")
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        Log.d(SECOND_ACTIVITY_TAG, "onRestoreInstanceState()_2")

    }

    override fun onRestart() {
        super.onRestart()
        Log.d(SECOND_ACTIVITY_TAG, "onRestart()_2")
    }

    companion object {
        private const val SECOND_ACTIVITY_TAG = "second activity"
        private const val NUMBER_KEY = "number_key"

        fun createIntent(context: Context, number: String): Intent {
            return Intent(context, SecondActivity::class.java).apply {
                putExtra(NUMBER_KEY, number)
            }
        }
    }

}