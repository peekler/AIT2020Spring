package hu.ait.aitsimplecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAdd.setOnClickListener {
            try {
                var a = etNumA.text.toString().toInt()
                var b = etNumB.text.toString().toInt()

                tvResult.text = "${a + b}"
            } catch (e: Exception) {
                etNumA.setError("This field can not be empty")
            }
        }

        btnMinus.setOnClickListener {
            var a = etNumA.text.toString().toInt()
            var b = etNumB.text.toString().toInt()

            tvResult.text = "${a-b}"
        }
    }
}
