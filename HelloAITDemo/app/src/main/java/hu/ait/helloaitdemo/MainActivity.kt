package hu.ait.helloaitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        btnShow.setOnClickListener {
            Toast.makeText(this,
                "Hi: "+ Date(System.currentTimeMillis()).toString(),
                Toast.LENGTH_LONG
                ).show()
        }
    }
}
