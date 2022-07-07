package com.thiagoaraujo.countdowntimer

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.thiagoaraujo.countdowntimer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            if (!validate()) {
                Toast.makeText(this, R.string.empty_field, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val timeInSeconds = binding.editTextLabel.text.toString().toLong()
            binding.editTextLabel.setText("")

            countdownTimer(timeInSeconds*1000)

            val service = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            service.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

            binding.editTextLabel.clearFocus()
        }
    }

    private fun countdownTimer(chosenTime: Long) {
        object : CountDownTimer(chosenTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.tvTimer.text = (millisUntilFinished / 1000).toString()
                binding.btnStart.isEnabled = false
            }

            override fun onFinish() {
                binding.tvTimer.text = getString(R.string.finished)
                binding.btnStart.isEnabled = true
            }

        }.start()
    }

    private fun validate(): Boolean {
        return binding.editTextLabel.text.isNotEmpty()
                &&  !binding.editTextLabel.text.toString().startsWith("0")
    }

}