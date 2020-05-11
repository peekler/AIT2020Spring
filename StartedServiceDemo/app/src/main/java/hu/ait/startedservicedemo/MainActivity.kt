package hu.ait.startedservicedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var intentTimeService = Intent(this, TimeService::class.java)

        btnStart.setOnClickListener {
            startService(intentTimeService)
        }

        btnStop.setOnClickListener {
            stopService(intentTimeService)
        }
    }
}
