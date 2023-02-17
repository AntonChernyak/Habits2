package com.antoncherniak.habits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
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

    companion object{
        private const val SECOND_ACTIVITY_TAG = "second activity"
    }
}