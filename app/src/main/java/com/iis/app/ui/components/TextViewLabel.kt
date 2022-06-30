package com.iis.app.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.iis.app.R

class TextViewLabel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes)  {


    init {
        LayoutInflater.from(context).inflate(R.layout.text_view_label, this, true)

        orientation = VERTICAL

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.TextViewLabel, 0, 0)

            //val title = resources.getText(typedArray.getResourceId(R.styleable.TextViewWithTitle_title, 0))
            val text = typedArray.getString(R.styleable.TextViewLabel_text)

            val titleView = findViewById<TextView>(R.id.text_view_label_text)
            titleView.text = text


            typedArray.recycle()
        }



    }

}