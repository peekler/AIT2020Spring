package hu.ait.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import hu.ait.tictactoe.model.TicTacToeModel
import hu.ait.tictactoe.ui.TicTacToeView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnRestart.setOnClickListener {
            ticView.restart()

            revealTicTacToe()
        }
    }


    public fun TicTacToeView.restart() {
        TicTacToeModel.resetModel()
        invalidate()
    }


    public fun showStatusText(message: String){
        tvStatus.text = message
    }


    fun revealTicTacToe() {
        val x = ticView.getRight()
        val y = ticView.getBottom()

        val startRadius = 0
        val endRadius = Math.hypot(ticView.getWidth().toDouble(),
            ticView.getHeight().toDouble()).toInt()

        val anim = ViewAnimationUtils.createCircularReveal(
            ticView,
            x,
            y,
            startRadius.toFloat(),
            endRadius.toFloat()
        )

        ticView.setVisibility(View.VISIBLE)
        anim.start()
    }


}
