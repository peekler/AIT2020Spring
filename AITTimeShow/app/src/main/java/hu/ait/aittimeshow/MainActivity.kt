package hu.ait.aittimeshow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnTime.setOnClickListener {
            var time =
                "${etName.text.toString()}, time is: ${Date(System.currentTimeMillis()).toString()}"

            Toast.makeText(this,
                time,
                Toast.LENGTH_LONG
                ).show()

            tvTime.text = time
        }

    }
}
