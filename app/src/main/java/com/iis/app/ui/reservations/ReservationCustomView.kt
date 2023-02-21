package com.iis.app.ui.reservations

import android.annotation.SuppressLint
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
import com.iis.app.ui.TextRect


//class ReservationCustomView @JvmOverloads constructor( context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0 ) : View(context, attrs, defStyleAttr){
@SuppressLint("ViewConstructor")
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

        val leftOrigin = 180
        val width = 200
        val spaceBetweenEvents = 10



        var left = leftOrigin
        var right =  left+width
        var rect = Rect(0, 0, 0, 0)

        eventsList.forEach{

            //dibujamos el rectangulo actual


            if( intersectsInTime(it,drawableReservations)){
                left= right+spaceBetweenEvents
            }else{
                left= leftOrigin
            }

            right = left+width

            rect = Rect(left, findHour(it.startTime)!!.rect.top, right, findHour(it.endTime)!!.rect.top)
            drawableReservations.add(DrawableReservation(it,rect ))



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

        textPaint.color = ContextCompat.getColor(context, R.color.white)
        textPaint.style = Paint.Style.FILL
        textPaint.textSize = 50F
        textPaint.textAlign = Paint.Align.CENTER


        drawableHours.forEach{
            paint.color = ContextCompat.getColor(context, R.color.colorGray2)
            canvas.drawRect(it.rect, paint)
            paint.color = ContextCompat.getColor(context, R.color.white)
            canvas.drawLine(it.rect.left.toFloat(),it.rect.bottom.toFloat(),it.rect.right.toFloat(),it.rect.bottom.toFloat(),paint)
            canvas.drawText(it.hour, it.rect.centerX().toFloat(), it.rect.centerY().toFloat() ,textPaint)


        }


    }

    private fun intersectsInTime(reservation:Reservation,drawnRectangles:  ArrayList<DrawableReservation>) :Boolean{
        //var intersects = false


        drawnRectangles.forEach {
            if(reservation.startTime < it.reservation.endTime && reservation.endTime > it.reservation.startTime ){
               return true
            }

        }


        return false
    }

    private fun drawEvents(canvas: Canvas){
        textPaint.color = ContextCompat.getColor(context, R.color.white)
        textPaint.style = Paint.Style.FILL
        textPaint.textSize = 30F
        textPaint.textAlign = Paint.Align.LEFT

        //var drawnRectangles = ArrayList<ReservationCustomView.DrawableReservation>()

        drawableReservations.forEach{
            paint.color = Color.parseColor(it.reservation.color.trim())
            canvas.drawRect(it.rect, paint)
            var textRect = TextRect(textPaint)
            //textRect.prepare(it.reservation.name, it.rect.right - it.rect.left,    it.rect.bottom - it.rect.top)
            //TODO filtrar por tipo de usuario para mostrar los datos
            textRect.prepare(it.reservation.spaceName+": Horario reservado", it.rect.right - it.rect.left,    it.rect.bottom - it.rect.top)
            textRect.draw(canvas,   it.rect.left, it.rect.top);
        }
    }


    private fun findHour(time:String): DrawableHour? {
        return  drawableHours.find { it.hour == time }
    }



    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        Log.v("ReservationCustomView","onMeasure ")

        // new width you want
        val newWid = 1500

        // new height you want
        val newht = 2600
        val wM = MeasureSpec.getMode(widthMeasureSpec)
        val wS = MeasureSpec.getSize(widthMeasureSpec)
        val hM = MeasureSpec.getMode(heightMeasureSpec)
        val hS = MeasureSpec.getSize(heightMeasureSpec)

        Log.v("ReservationCustomView","onMeasure wS " +MeasureSpec.toString(widthMeasureSpec))
        Log.v("ReservationCustomView"," onMeasure hS "+ MeasureSpec.toString(heightMeasureSpec))

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
    inner class DrawableReservation(val reservation: Reservation, val rect: Rect, drawn: Boolean=false)

}



