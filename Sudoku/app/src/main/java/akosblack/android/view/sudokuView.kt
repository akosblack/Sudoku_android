package akosblack.android.view

import akosblack.android.GameActivity
import akosblack.android.R
import akosblack.android.model.SudokuModel
import akosblack.android.model.SudokuModel.number0
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlin.math.min


class SudokuView : View {


    private var paintText = Paint()
    private val paintBg = Paint()
    private val paintLine = Paint()
    private val paintCircle = Paint()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    init {
        paintBg.color = Color.BLACK
        paintBg.style = Paint.Style.FILL

        paintLine.color = Color.WHITE
        paintLine.style = Paint.Style.STROKE

        paintCircle.color = Color.GRAY
        paintCircle.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRect(0F, 0F, width.toFloat(), height.toFloat(), paintBg)
        drawCircle(canvas)
        drawGameArea(canvas)
        if(SudokuModel.sudoku.currentNumber != SudokuModel.sudoku.prevNumber)
            invalidate()
        drawNumbers(canvas)
    }

    private fun drawGameArea(canvas: Canvas) {
        val widthFloat: Float = width.toFloat()
        val heightFloat: Float = height.toFloat()

      /*  // border
        paintLine.strokeWidth = 15F
        canvas.drawRect(0F, 0F, widthFloat, heightFloat, paintLine)*/

        paintLine.strokeWidth = 5F
        // eight horizontal lines and
        /*for(i in 0 until 9) {

            canvas.drawLine(0F, i * heightFloat / 9, widthFloat, i * heightFloat / 9, paintLine)
            canvas.drawLine(i * widthFloat / 9, 0F, i * widthFloat / 9, heightFloat, paintLine)
        }*/

        for(i in 1 until 9) {
            canvas.drawLine(20F, i * heightFloat / 9, widthFloat-20F, i * heightFloat / 9, paintLine)
            canvas.drawLine(i * widthFloat / 9, 20F, i * widthFloat / 9, heightFloat-20F, paintLine)
        }

        //make it thick
        paintLine.strokeWidth = 13F
        canvas.drawLine(0F, 3 * heightFloat / 9, widthFloat, 3 * heightFloat / 9, paintLine)
        canvas.drawLine(0F, 6 * heightFloat / 9, widthFloat, 6 * heightFloat / 9, paintLine)
        canvas.drawLine(3 * widthFloat / 9, 0F, 3 * widthFloat / 9, heightFloat, paintLine)
        canvas.drawLine(6 * widthFloat / 9, 0F, 6 * widthFloat / 9, heightFloat, paintLine)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = MeasureSpec.getSize(widthMeasureSpec)
        val h = MeasureSpec.getSize(heightMeasureSpec)
        val d: Int

        when {
            w == 0 -> { d = h }
            h == 0 -> { d = w }
            else -> { d = min(w, h) }
        }

        setMeasuredDimension(d, d)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                if(SudokuModel.sudoku.currentNumber != SudokuModel.sudoku.prevNumber)
                    invalidate()
                val tX: Int = (event.x / (width / 9)).toInt()
                val tY: Int = (event.y / (height / 9)).toInt()
                //beírás
                if (tX < 9 && tY < 9 && SudokuModel.sudoku.getFieldContent(tX, tY) == number0) {
                    SudokuModel.sudoku.setFieldContent(tX, tY, SudokuModel.sudoku.currentNumber)
                    SudokuModel.sudoku.hasEMPTY--
                    if(SudokuModel.sudoku.hasEMPTY == 0)
                        (context as GameActivity).endGame()
                    invalidate()
                }
                //törlés
                if(tX < 9 && tY < 9 && SudokuModel.sudoku.currentNumber==SudokuModel.EMPTY && !SudokuModel.sudoku.isBaseElement[tX][tY]){
                    SudokuModel.sudoku.setFieldContent(tX,tY,0)
                    SudokuModel.sudoku.hasEMPTY++
                    invalidate()
                }
                return true
            }
            else -> return super.onTouchEvent(event)
        }
    }

    private fun drawCircle(canvas: Canvas){
        for(i in 0 until 9){
            for (j in 0 until 9){

                if (SudokuModel.sudoku.getFieldContent(i, j) == SudokuModel.sudoku.currentNumber) {
                    val centerX = (i * width / 9 + width / 9 - 59).toFloat()
                    val centerY = (j * height / 9 + height / 9 - 59).toFloat()

                    canvas.drawCircle(centerX, centerY, (width / 24).toFloat(), paintCircle)
                    //postOnAnimationDelayed(canvas.drawCircle(centerX,centerY,(width/24).toFloat(),paintCircle), 10)
                }

                if (SudokuModel.sudoku.getFieldContent(i,j) != 0 && !SudokuModel.sudoku.isBaseElement[i][j] && SudokuModel.sudoku.currentNumber == SudokuModel.EMPTY){
                    val centerX = (i * width / 9 + width / 9 - 59).toFloat()
                    val centerY = (j * height / 9 + height / 9 - 59).toFloat()
                    canvas.drawCircle(centerX, centerY, (width / 24).toFloat(), paintCircle)

                }
            }
        }

    }

    private fun drawNumbers(canvas: Canvas) {
        paintText.setTextSize(65.toFloat());
        paintText.color=Color.WHITE
         for (i in 0 until 9) {
            for (j in 0 until 9) {
                when (SudokuModel.sudoku.getFieldContent(i, j)) {
                    SudokuModel.number1 -> {
                        paintText.color=Color.WHITE
                        if(!SudokuModel.sudoku.isBaseElement[i][j])
                            paintText.color=Color.YELLOW
                        val centerX = (i * width / 9 + width / 9-75).toFloat()
                        val centerY = (j * height / 9 + height/ 9-40).toFloat()
                        canvas.drawText( "1", centerX, centerY, paintText)
                    }
                    SudokuModel.number2 -> {
                        paintText.color=Color.WHITE
                        if(!SudokuModel.sudoku.isBaseElement[i][j])
                            paintText.color=Color.YELLOW
                        val centerX = (i * width / 9 + width / 9-75).toFloat()
                        val centerY = (j * height / 9 + height/ 9-40).toFloat()
                        canvas.drawText( "2", centerX, centerY, paintText)
                    }
                    SudokuModel.number3 -> {
                        paintText.color=Color.WHITE
                        if(!SudokuModel.sudoku.isBaseElement[i][j])
                            paintText.color=Color.YELLOW
                        val centerX = (i * width / 9 + width / 9-75).toFloat()
                        val centerY = (j * height / 9 + height/ 9-40).toFloat()
                        canvas.drawText( "3", centerX, centerY, paintText)
                    }SudokuModel.number4 -> {
                    paintText.color=Color.WHITE
                    if(!SudokuModel.sudoku.isBaseElement[i][j])
                        paintText.color=Color.YELLOW
                    val centerX = (i * width / 9 + width / 9-75).toFloat()
                    val centerY = (j * height / 9 + height/ 9-40).toFloat()
                    canvas.drawText( "4", centerX, centerY, paintText)
                }
                    SudokuModel.number5 -> {
                        paintText.color=Color.WHITE
                        if(!SudokuModel.sudoku.isBaseElement[i][j])
                            paintText.color=Color.YELLOW
                        val centerX = (i * width / 9 + width / 9-75).toFloat()
                        val centerY = (j * height / 9 + height/ 9-40).toFloat()
                        canvas.drawText( "5", centerX, centerY, paintText)
                    }
                    SudokuModel.number6 -> {
                        paintText.color=Color.WHITE
                        if(!SudokuModel.sudoku.isBaseElement[i][j])
                            paintText.color=Color.YELLOW
                        val centerX = (i * width / 9 + width / 9-75).toFloat()
                        val centerY = (j * height / 9 + height/ 9-40).toFloat()
                        canvas.drawText( "6", centerX, centerY, paintText)
                    }
                    SudokuModel.number7 -> {
                        paintText.color=Color.WHITE
                        if(!SudokuModel.sudoku.isBaseElement[i][j])
                            paintText.color=Color.YELLOW

                        val centerX = (i * width / 9 + width / 9-75).toFloat()
                        val centerY = (j * height / 9 + height/ 9-40).toFloat()
                        canvas.drawText( "7", centerX, centerY, paintText)
                    }
                    SudokuModel.number8 -> {
                        paintText.color=Color.WHITE
                        if(!SudokuModel.sudoku.isBaseElement[i][j])
                            paintText.color=Color.YELLOW
                        val centerX = (i * width / 9 + width / 9-75).toFloat()
                        val centerY = (j * height / 9 + height/ 9-40).toFloat()
                        canvas.drawText( "8", centerX, centerY, paintText)
                    }
                    SudokuModel.number9 -> {
                        paintText.color=Color.WHITE
                        if(!SudokuModel.sudoku.isBaseElement[i][j])
                            paintText.color=Color.YELLOW
                        val centerX = (i * width / 9 + width / 9-75).toFloat()
                        val centerY = (j * height / 9 + height/ 9-40).toFloat()
                        canvas.drawText( "9", centerX, centerY, paintText)
                    }

                }
            }
        }
    }
}




