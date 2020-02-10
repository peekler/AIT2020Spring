package hu.ait.aittimeshow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var myTesla = Car("Cybertruck", 299)
        tvTime.text = myTesla.type


        var myBoat = Boat()
        myBoat.show()

        btnTime.setOnClickListener {
            Log.d("KEY_TAG", "time button was clicked")


            var name = getString(R.string.text_name)

            var time =
                getString(R.string.text_date,
                    etName.text.toString(),
                    Date(System.currentTimeMillis()).toString())

            // <string name="text_date">%1$s, time is: %2$s</string>

            //"${etName.text.toString()}, time is: ${Date(System.currentTimeMillis()).toString()}"

            //Toast.makeText(this,
            //    time,
            //    Toast.LENGTH_LONG
            //    ).show()

            tvTime.text = time

            Snackbar.make(layoutMain, time, Snackbar.LENGTH_LONG).show()

        }

    }
}
