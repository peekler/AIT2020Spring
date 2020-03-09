package hu.ait.threadandtimerdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.util.*

class MainActivity : AppCompatActivity() {

    var enabled = false

    inner class MyThread : Thread() {
        override fun run() {
            while (enabled) {
                runOnUiThread {
                    tvData.append("#")
                }

                sleep(1000)
            }
        }
    }

    inner class MyTimerTask : TimerTask() {
        override fun run() {
            runOnUiThread {
                tvData.append("X")
            }
        }
    }

    var timerMain: Timer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStart.setOnClickListener {
            enabled = true
            //MyThread().start()
            if (timerMain == null) {
                timerMain = Timer()
            }
            timerMain?.schedule(MyTimerTask(), 2000, 500)

        }

        btnStop.setOnClickListener {
            enabled = false

            timerMain?.cancel()
            timerMain = null
        }
    }

    override fun onStop() {
        super.onStop()
        enabled = false

        try {
            timerMain?.cancel()
            timerMain = null
        } catch (e: Exception){
            e.printStackTrace()
        }
    }
}
