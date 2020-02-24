package hu.ait.highlowgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*

class GameActivity : AppCompatActivity() {

    var generatedNum = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        btnGuess.setOnClickListener {
            var myNum = etNum.text.toString().toInt()

            if (myNum == generatedNum) {
                tvResult.text = "Congratulations"
            } else if (myNum < generatedNum) {
                tvResult.text = "The number is higher"
            } else if (myNum > generatedNum) {
                tvResult.text = "The number is lower"
            }
        }


        generateNumber()
    }

    fun generateNumber() {
        var rand = Random(System.currentTimeMillis())
        generatedNum = rand.nextInt(100) // generates number between 0..99
    }
}
