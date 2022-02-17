package com.example.habits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.habits.FirstActivity.Companion.COUNTER_KEY
import com.example.habits.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private val viewBinding: ActivitySecondBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val number = intent.getStringExtra(COUNTER_KEY)?.toInt() ?: 0
        viewBinding.squareTextView.text = (number * number).toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SQUARE_KEY, viewBinding.squareTextView.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val squareNumber = savedInstanceState.getString(SQUARE_KEY, "0")
        viewBinding.squareTextView.text = squareNumber
    }

    companion object {
        private const val SQUARE_KEY = "square key"
    }
}