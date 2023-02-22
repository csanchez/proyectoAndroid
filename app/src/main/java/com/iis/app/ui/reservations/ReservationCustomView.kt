package com.iis.app.ui.reservations

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.ScaleGestureDetector
import android.view.View
import androidx.core.content.ContextCompat
import com.iis.app.R
import com.iis.app.data.model.Reservation
import com.iis.app.ui.components.TextRect


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

    private var detector: ScaleGestureDetector = ScaleGestureDetector(context, ScaleListener())
    private var dragging: Boolean = false // May be unnecessary

    private var scaleFactor: Float = 1F
    private val MIN_ZOOM: Float = 0.5f
    private val MAX_ZOOM: Float = 2f

    private var initX: Float = 0f // See onTouchEvent
    private var initY: Float = 0f // See onTouchEvent

    private var canvasX: Float = 0f // x-coord of canvas center
    private var canvasY: Float = 0f // y-coord of canvas center

    init {

        fillHours()
        fillEvents(eventsList)
    }

    //Transaltion https://blog.devgenius.io/canvas-translation-and-state-management-custom-views-on-android-part-2-1428171b4eef
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        initPaints()

        canvas.scale(scaleFactor, scaleFactor)
        canvas.translate(canvasX, canvasY)



        drawHours(canvas)
        drawLines(canvas )
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
            paint.color = ContextCompat.getColor(context, R.color.textColor)
            canvas.drawLine(it.rect.left.toFloat(),it.rect.bottom.toFloat(),finalCanvasW.toFloat(),it.rect.bottom.toFloat(),paint)

        }
    }
    
    private fun drawHours(canvas: Canvas){

        textPaint.color = ContextCompat.getColor(context, R.color.textColor)
        textPaint.style = Paint.Style.FILL
        textPaint.textSize = 50F
        textPaint.textAlign = Paint.Align.CENTER


        drawableHours.forEach{
            paint.color = ContextCompat.getColor(context, R.color.contentBG2)
            canvas.drawRect(it.rect, paint)
            //paint.color = ContextCompat.getColor(context, R.color.textColor)
            //canvas.drawLine(it.rect.left.toFloat(),it.rect.bottom.toFloat(),it.rect.right.toFloat(),it.rect.bottom.toFloat(),paint)

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

        val padding =  10

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



    /*// When the user pinches the view, zoom in, when they reverse pinch the view, zoom out
    private val scaleGestureDetector = ScaleGestureDetector(context, object :
        ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector?): Boolean {
            Log.d("scaleGestureDetector")
            detector?.let {
                scaleFactor *= detector.scaleFactor
                scaleFactor = Math.max(MIN_ZOOM, Math.min(scaleFactor, MAX_ZOOM))
                //scaleX *= detector.scaleFactor
                //scaleY *= detector.scaleFactor
                invalidate()
            }
            return true
        }
    }) */

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            // Self-explanatory
            //Log.d("onScale", detector.scaleFactor.toString())
            scaleFactor *= detector.scaleFactor
            // If scaleFactor is less than 0.5x, default to 0.5x as a minimum. Likewise, if
            //  scaleFactor is greater than 10x, default to 10x zoom as a maximum.
            scaleFactor = Math.max(MIN_ZOOM, Math.min(scaleFactor, MAX_ZOOM))
            Log.d("onScale scaleFactor", scaleFactor.toString())
            invalidate() // Re-draw the canvas

            return true
        }
    }

    /*
        https://github.com/arjunr00/android-pan-zoom-canvas/blob/master/app/src/main/java/com/arjunraghavan/android/pannablezoomablecanvas/PanZoomCanvasView.kt
        https://github.com/husaynhakeem/android-playground/blob/master/GesturesSample/app/src/main/java/com/husaynhakeem/gesturessample/DrawingCanvas.kt

        https://proandroiddev.com/detecting-touch-gestures-in-android-f8eb4a4faf98
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        // Handle the different types of actions
        Log.d("onTouchEvent", event?.actionMasked.toString())
        when (event?.action) {

            ACTION_DOWN -> {
                // Might not be necessary; check out later
                dragging = true
                // We want to store the coords of the user's finger as it is before they move
                //  in order to calculate dx and dy
                initX =event.x
                initY = event.y
            }
            ACTION_MOVE -> {
                // Self explanatory; the difference in x- and y-coords between successive calls to
                //  onTouchEvent
                val dx: Float = event.x - initX
                val dy: Float = event.y - initY

                Log.d("TOUCHEVENT", "Direction" + "dx: $dx, dy: $dy")

               // if (dragging) {
                    // Move the canvas dx units right and dy units down
                    // dx and dy are divided by scaleFactor so that panning speeds are consistent
                    //  with the zoom level


                    var factorX =  dx/scaleFactor
                    var factorY =  dy/scaleFactor
                    Log.d("TOUCHEVENT", "movement canvas" + "factorX: $factorX, factorY: $factorY")

                    if (canvasX <= 0)
                        canvasX += factorX
                    if (canvasY <= 0)
                        canvasY += factorY

                    if (canvasX > 0)
                        canvasX = 0F
                    if (canvasY > 0)
                        canvasY = 0F
                    //canvasY *= -1
                    //canvasX *= -1

                    invalidate() // Re-draw the canvas

                    // Change initX and initY to the new x- and y-coords
                    initX =event.x
                    initY = event.y
               // }
            }
            ACTION_POINTER_UP -> {
                // This sets initX and initY to the position of the pointer finger so that the
                //  screen doesn't jump when it's lifted with the main finger still down
                initX = x
                initY = y
            }
            ACTION_UP -> dragging = false // Again, may be unnecessary



        }

        // Let the scale gesture detector analyze the event. If a scale gesture is detected, `onScale()` is invoked
        //scaleGestureDetector.onTouchEvent(event)
        detector.onTouchEvent(event)

        val x = event?.x
        val y = event?.y
        Log.d("TOUCHEVENT", "x: $x, y: $y,initY: $initX, initY," + "canvasX: $canvasX, canvasY: $canvasY")
        // Data pertaining to fingers for responsiveness and stuff
        Log.d("TOUCHEVENT", "Action: ${event?.action?.and(MotionEvent.ACTION_MASK)}\n")

        return true
    }





    inner class DrawableHour(val hour: String, val rect: Rect)

    //inner class DrawableReservation(reservation: Reservation, rect: Rect){
    inner class DrawableReservation(val reservation: Reservation, val rect: Rect, drawn: Boolean=false)

}



