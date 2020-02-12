package hu.ait.tictactoe.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class TicTacToeView(contex: Context?, attrs: AttributeSet?) : View(contex, attrs) {

    var paintBackGround = Paint()
    var paintLine = Paint()

    var x = -1
    var y = -1


    init {
        paintBackGround.color = Color.BLACK
        paintBackGround.style = Paint.Style.FILL

        paintLine.color = Color.WHITE
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 5f
    }



    override fun onDraw(canvas: Canvas?) {
        canvas?.drawRect(0f, 0f, width.toFloat(),
            height.toFloat(), paintBackGround)

        canvas?.drawLine(0f, 0f, width.toFloat(), height.toFloat(), paintLine)

        if (x != -1 && y != -1) {
            canvas?.drawCircle(x.toFloat(), y.toFloat(), 60f, paintLine)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            x = event.x.toInt()
            y = event.y.toInt()

            invalidate() // redraws the view, the onDraw(...) will be called
        }

        return true
    }


}