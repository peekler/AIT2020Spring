package hu.ait.tictactoe.ui

import android.content.Context
import android.graphics.*
import android.media.tv.TvView
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import hu.ait.tictactoe.MainActivity
import hu.ait.tictactoe.R
import hu.ait.tictactoe.model.TicTacToeModel

class TicTacToeView(contex: Context?, attrs: AttributeSet?) : View(contex, attrs) {

    var paintBackGround = Paint()
    var paintLine = Paint()
    var paintText = Paint()

    var bitmapBg = BitmapFactory.decodeResource(
        contex?.resources,
        R.drawable.background)

    init {
        paintBackGround.color = Color.BLACK
        paintBackGround.style = Paint.Style.FILL

        paintLine.color = Color.WHITE
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 5f

        paintText.color = Color.GREEN
        paintText.textSize = 60f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        paintText.textSize = height / 3f

        bitmapBg = Bitmap.createScaledBitmap(bitmapBg, width / 3,
            height / 3, false)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawRect(0f, 0f, width.toFloat(),
            height.toFloat(), paintBackGround)


        canvas?.drawBitmap(bitmapBg, 0f, 0f, null)


        canvas?.drawText("1", 0f, height/3f, paintText)


        drawBoard(canvas)

        drawPlayers(canvas)
    }

    private fun drawBoard(canvas: Canvas?) {
        // border
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintLine)
        // two horizontal lines
        canvas?.drawLine(
            0f, (height / 3).toFloat(), width.toFloat(), (height / 3).toFloat(),
            paintLine
        )
        canvas?.drawLine(
            0f, (2 * height / 3).toFloat(), width.toFloat(),
            (2 * height / 3).toFloat(), paintLine
        )

        // two vertical lines
        canvas?.drawLine(
            (width / 3).toFloat(), 0f, (width / 3).toFloat(), height.toFloat(),
            paintLine
        )
        canvas?.drawLine(
            (2 * width / 3).toFloat(), 0f, (2 * width / 3).toFloat(), height.toFloat(),
            paintLine
        )
    }

    private fun drawPlayers(canvas: Canvas?) {
        for (i in 0..2) {
            for (j in 0..2) {
                if (TicTacToeModel.getFieldContent(i, j) == TicTacToeModel.CIRCLE) {
                    val centerX = (i * width / 3 + width / 6).toFloat()
                    val centerY = (j * height / 3 + height / 6).toFloat()
                    val radius = height / 6 - 2

                    canvas?.drawCircle(centerX, centerY, radius.toFloat(), paintLine)
                } else if (TicTacToeModel.getFieldContent(i, j) == TicTacToeModel.CROSS) {
                    canvas?.drawLine((i * width / 3).toFloat(), (j * height / 3).toFloat(),
                        ((i + 1) * width / 3).toFloat(),
                        ((j + 1) * height / 3).toFloat(), paintLine)

                    canvas?.drawLine(((i + 1) * width / 3).toFloat(), (j * height / 3).toFloat(),
                        (i * width / 3).toFloat(), ((j + 1) * height / 3).toFloat(), paintLine)
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            // 0,1; 0,2...
            val tX = event.x.toInt() / (width/3)
            val tY = event.y.toInt() / (height/3)

            if (tX < 3 && tY < 3 &&
                TicTacToeModel.getFieldContent(tX, tY) == TicTacToeModel.EMPTY) {

                TicTacToeModel.setFieldContent(tX, tY, TicTacToeModel.getNextPlayer())

                TicTacToeModel.changeNextPlayer()

                invalidate() // redraws the view, the onDraw(...) will be called

                //Toast.makeText((context as MainActivity), "win", Toast.LENGTH_LONG).show()

                var nextPlayer =
                    if (TicTacToeModel.getNextPlayer() == TicTacToeModel.CIRCLE) "O" else "X"

                (context as MainActivity).showStatusText(
                    context.resources.getString(R.string.text_next_player, nextPlayer)
                )

            }
        }

        return true
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = View.MeasureSpec.getSize(widthMeasureSpec)
        val h = View.MeasureSpec.getSize(heightMeasureSpec)
        val d = if (w == 0) h else if (h == 0) w else if (w < h) w else h
        setMeasuredDimension(d, d)
    }



    public fun restart() {
        TicTacToeModel.resetModel()
        invalidate()
    }


}