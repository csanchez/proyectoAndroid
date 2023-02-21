package com.iis.app.ui.reservations

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.iis.app.R
import com.iis.app.data.model.Reservation


//class ReservationCustomView @JvmOverloads constructor( context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0 ) : View(context, attrs, defStyleAttr){
class ReservationCustomView @JvmOverloads constructor(val eventsList:  ArrayList<Reservation>, context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr){
    var paint: Paint = Paint()
    var textPaint: Paint = Paint()

    //val eventsList = eventsList

    var drawableHours = ArrayList<ReservationCustomView.DrawableHour>()
    var drawableReservations = ArrayList<ReservationCustomView.DrawableReservation>()

    var finalCanvasH = 0
    var finalCanvasW = 0


    init {

        fillHours()
        fillEvents(eventsList)
    }

    override fun onDraw(canvas: Canvas) {
        initPaints()


        drawLines(canvas )
        drawHours(canvas)
        drawEvents(canvas)



    }





    private fun initPaints(){
        paint.color = ContextCompat.getColor(context, R.color.colorGray2)
        paint.style = Paint.Style.FILL

        textPaint.color = ContextCompat.getColor(context, R.color.white)
        textPaint.style = Paint.Style.FILL
        textPaint.textSize = 50F
        textPaint.textAlign = Paint.Align.CENTER
    }

    private fun fillHours(){
        val left = 0
        var top = 0
        val right = 150
        var bottom = 100

        val heigh = 100

        var w = 0F //textPaint.measureText(jdk.internal.org.jline.utils.Colors.s) / 2
        val textSize: Float = textPaint.textSize
        var text: String
        var rect = Rect(0,0,0,0)

            for (hour in 8..20){
                text = String.format("%02d:00", hour)
                w= textPaint.measureText(text) / 2
                //rect = Rect(left - w.toInt(), top - textSize.toInt(), right + w.toInt(), bottom)
                rect = Rect(left , top , right , bottom)

                drawableHours.add(DrawableHour(text,rect))




                top = bottom
                bottom += heigh

                text = String.format("%02d:30", hour)
                w= textPaint.measureText(text) / 2
                //rect = Rect(left - w.toInt(), top - textSize.toInt(), right + w.toInt(), bottom)
                rect = Rect(left , top , right , bottom)
                drawableHours.add(DrawableHour(text,rect))

                top = bottom
                bottom += heigh

                finalCanvasH += 2*heigh
        }

        finalCanvasW+=150
    }

    private fun fillEvents(eventsList:  ArrayList<Reservation>){
        val width = 200
        val spaceBetweenEvents = 10
        var left = 180
        var right =  left+width


        eventsList.forEach{
            val startRect = findHour(it.startTime)!!.rect
            val endRect = findHour(it.endTime)!!.rect
            val rect = Rect(left, startRect.top, right, endRect.top)
            drawableReservations.add(DrawableReservation(it,rect ))
            Log.d("CALENDAR DAY", rect.toString())
            left= right+spaceBetweenEvents
            right = left+width
            finalCanvasW+=width+spaceBetweenEvents
        }
        finalCanvasW+=100
    }

    private fun drawLines(canvas: Canvas){
        drawableHours.forEach{
            paint.color = ContextCompat.getColor(context, R.color.colorGray6)
            canvas.drawLine(it.rect.left.toFloat(),it.rect.bottom.toFloat(),finalCanvasW.toFloat(),it.rect.bottom.toFloat(),paint)

        }
    }
    
    private fun drawHours(canvas: Canvas){
        drawableHours.forEach{
            paint.color = ContextCompat.getColor(context, R.color.colorGray2)
            canvas.drawRect(it.rect, paint)
            paint.color = ContextCompat.getColor(context, R.color.white)
            canvas.drawLine(it.rect.left.toFloat(),it.rect.bottom.toFloat(),it.rect.right.toFloat(),it.rect.bottom.toFloat(),paint)
            canvas.drawText(it.hour, it.rect.centerX().toFloat(), it.rect.centerY().toFloat() ,textPaint)
        }


    }


    private fun drawEvents(canvas: Canvas){

        drawableReservations.forEach{
            Log.d("ReservationCustomView","color ---"+it.reservation.color+"---")
            paint.color = Color.parseColor("#14C38E") // Color.parseColor(it.reservation.color)
            val colorCOde:String = it.reservation.color.trim()
            paint.color = Color.parseColor(it.reservation.color.trim())
            canvas.drawRect(it.rect, paint)

           // canvas.drawText(it.hour, it.rect.centerX().toFloat(), it.rect.centerY().toFloat() ,textPaint)
        }
    }


    private fun findHour(time:String): DrawableHour? {
        return  drawableHours.find { it.hour == time }
    }




    /*private fun measureDimension(desiredSize: Int, measureSpec: Int): Int {
        var result: Int
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            result = desiredSize
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize)
            }
        }
        if (result < desiredSize) {
            Log.e("ChartView", "The view is too small, the content might get cut")
        }
        return result
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        Log.v("Chart onMeasure w", MeasureSpec.toString(widthMeasureSpec))
        Log.v("Chart onMeasure h", MeasureSpec.toString(heightMeasureSpec))
        val desiredWidth = suggestedMinimumWidth + paddingLeft + paddingRight
        val desiredHeight = suggestedMinimumHeight + paddingTop + paddingBottom
        Log.v("Chart desiredWidth", desiredWidth.toString()+" -"+ measureDimension(desiredWidth, widthMeasureSpec).toString())
        Log.v("Chart desiredHeight", desiredHeight.toString()+" - "+  measureDimension(desiredHeight, heightMeasureSpec).toString())

        Log.v("Chart finalCanvasW", finalCanvasW.toString())
        Log.v("Chart finalCanvasH", finalCanvasH.toString())

        setMeasuredDimension(
            990, //measureDimension(desiredWidth, widthMeasureSpec),
            1520 //measureDimension(desiredHeight, heightMeasureSpec)
        )
    } */


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        Log.v("ReservationCustomView","onMeasure ")
        Log.v("ReservationCustomView","onMeasure w " +MeasureSpec.toString(widthMeasureSpec))
        Log.v("ReservationCustomView"," onMeasure h "+ MeasureSpec.toString(heightMeasureSpec))
        // new width you want
        val newWid = 500

        // new height you want
        val newht = 2600
        val wM = MeasureSpec.getMode(widthMeasureSpec)
        val wS = MeasureSpec.getSize(widthMeasureSpec)
        val hM = MeasureSpec.getMode(heightMeasureSpec)
        val hS = MeasureSpec.getSize(heightMeasureSpec)

        // Measure Width custom view
        val width: Int = if (wM == MeasureSpec.EXACTLY) {
            // Must be of width size
            wS
        } else if (wM == MeasureSpec.AT_MOST) {
            // Can't be bigger than new
            // width and width size
            Math.min(newWid, wS)
        } else {
            // Be whatever you want
            finalCanvasW
        }

        // Measure Height of custom view
        val height: Int = if (hM == MeasureSpec.EXACTLY) {
            // Must be of height size
            hS
        } else if (hM == MeasureSpec.AT_MOST) {
            // Can't be bigger than new
            // height and height size
            Math.min(newht, hS)
        } else {
            // Be whatever you want
            finalCanvasH
        }

        Log.v("ReservationCustomView", "width $width")
        Log.v("ReservationCustomView", " height $height")

        Log.v("Chart finalCanvasW", finalCanvasW.toString())
        Log.v("Chart finalCanvasH", finalCanvasH.toString())
        // for making the desired size
        setMeasuredDimension(width, height)
    }










    inner class DrawableHour(val hour: String, val rect: Rect)

    //inner class DrawableReservation(reservation: Reservation, rect: Rect){
    inner class DrawableReservation(val reservation: Reservation, val rect: Rect)

}



