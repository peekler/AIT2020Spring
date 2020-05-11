package hu.ait.wearosdemo

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enables Always-on
        setAmbientEnabled()

        btnShow.setOnClickListener {
            Toast.makeText(this, "Demo", Toast.LENGTH_LONG).show()
        }
    }
}
