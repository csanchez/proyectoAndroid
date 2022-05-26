package com.example.iisapp.ui.components

import android.R.color
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.iisapp.R


class TextViewWithTitle  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {



    init {
        LayoutInflater.from(context).inflate(R.layout.text_view_with_title, this, true)

        orientation = VERTICAL





        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.TextViewWithTitle, 0, 0)

            //val title = resources.getText(typedArray.getResourceId(R.styleable.TextViewWithTitle_title, 0))
            val title = typedArray.getString(R.styleable.TextViewWithTitle_title)

            //val body = resources.getText(typedArray.getResourceId(R.styleable.text_view_with_title_attributes_body, R.string.))
            val body = typedArray.getString(R.styleable.TextViewWithTitle_body)


            val titleView = findViewById<TextView>(R.id.text_view_with_title_title)
            titleView.text = title


            val bodyView = findViewById<TextView>(R.id.text_view_with_title_body)
            bodyView.text = body

            typedArray.recycle()
        }



    }

    fun setBody(body:String){
        val bodyView = findViewById<TextView>(R.id.text_view_with_title_body)
        bodyView.text = body
    }
}