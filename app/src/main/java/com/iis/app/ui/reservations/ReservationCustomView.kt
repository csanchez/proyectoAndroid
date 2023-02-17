package com.iis.app.ui.reservations

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.iis.app.R
import com.iis.app.data.model.Reservation


//class ReservationCustomView @JvmOverloads constructor( context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0 ) : View(context, attrs, defStyleAttr){
class ReservationCustomView @JvmOverloads constructor(eventsList:  ArrayList<Reservation>, context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr){
    var paint: Paint = Paint()
    var textPaint: Paint = Paint()

    val eventsList = eventsList

    var drawableHours = ArrayList<ReservationCustomView.DrawableHour>()
    var drawableReservations = ArrayList<ReservationCustomView.DrawableReservation>()




    override fun onDraw(canvas: Canvas) {
        initPaints()
        initHours(canvas)




        eventsList.forEach{

            val textView = View.inflate(context, R.layout.event_calendar_day, null) as androidx.constraintlayout.widget.ConstraintLayout

           // parentLayout.addView(textView);
        }

    }


    private fun drawRect(canvas: Canvas) {
        //canvas.     drawRect(backgroundRect, backgroundPaint)
        //canvas.drawRoundRect(RectF(backgroundRect), 50f, 50f, backgroundPaintStroke)
    }

    private fun initPaints(){
        paint.setColor(ContextCompat.getColor(context, R.color.colorGray2))
        paint.style = Paint.Style.FILL

        textPaint.setColor(ContextCompat.getColor(context, R.color.white))
        textPaint.style = Paint.Style.FILL
        textPaint.setTextSize(50F);
        textPaint.textAlign = Paint.Align.CENTER
    }

    private fun initHours(canvas: Canvas){
        var left = 0
        var top = 0
        var right = 150
        var bottom = 100

        val heigh = 100

        var w: Float = 0F //textPaint.measureText(jdk.internal.org.jline.utils.Colors.s) / 2
        val textSize: Float = textPaint.getTextSize()
        var text = ""
        var rect = Rect(0,0,0,0)

            for (hour in 8..20){
                text = String.format("%02d:00", hour)
                w= textPaint.measureText(text) / 2
                rect = Rect(left - w.toInt(), top - textSize.toInt(), right + w.toInt(), bottom)

                drawableHours.add(DrawableHour(text,rect))
                canvas.drawRect(rect, paint);
                canvas.drawText(text, rect.centerX().toFloat(), rect.centerY().toFloat() ,textPaint);



                top = bottom
                bottom = bottom + heigh

                text = String.format("%02d:30", hour)
                w= textPaint.measureText(text) / 2
                rect = Rect(left - w.toInt(), top - textSize.toInt(), right + w.toInt(), bottom)
                drawableHours.add(DrawableHour(text,rect))

                canvas.drawRect(rect, paint);
                canvas.drawText(text, rect.centerX().toFloat(), rect.centerY().toFloat() ,textPaint);

                top = bottom
                bottom = bottom + heigh




        }
    }


    inner class DrawableHour(hour: String, rect: Rect){
        val hour = hour
        val rect = rect
    }

    inner class DrawableReservation(reservation: Reservation, rect: Rect){
        val reservation = reservation
        val rect = rect
    }

}



