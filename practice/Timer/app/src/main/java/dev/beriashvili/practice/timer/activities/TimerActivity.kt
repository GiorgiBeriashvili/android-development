package dev.beriashvili.practice.timer.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dev.beriashvili.practice.timer.R
import dev.beriashvili.practice.timer.models.Time
import kotlinx.android.synthetic.main.activity_timer.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimerActivity : AppCompatActivity() {
    private lateinit var timer: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        init()
    }

    private fun init() {
        startButton.setOnClickListener {
            if (!::timer.isInitialized || timer.isCancelled || timer.isCompleted) {
                timer = CoroutineScope(IO).launch {
                    runTimer()
                }
            }
        }

        stopButton.setOnClickListener {
            CoroutineScope(IO).launch {
                if (::timer.isInitialized) {
                    timer.cancel()
                }
            }
        }
    }

    private suspend fun runTimer() {
        for (hour in Time.Hour.MIN until Time.Hour.MAX) {
            for (minute in Time.Minute.MIN until Time.Minute.MAX) {
                for (second in Time.Second.MIN until Time.Second.MAX) {
                    CoroutineScope(Main).launch {
                        timerTextView.text = Time.format(hour, minute, second)
                    }

                    delay(1000)
                }
            }
        }

        timer.invokeOnCompletion {
            CoroutineScope(Main).launch {
                Toast.makeText(this@TimerActivity, "Timer has finished!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}