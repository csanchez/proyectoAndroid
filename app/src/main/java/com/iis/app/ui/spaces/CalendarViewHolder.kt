package com.iis.app.ui.spaces

import com.iis.app.R
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iis.app.ui.spaces.CalendarAdapter.OnItemListener


class CalendarViewHolder private constructor(itemView: View, onItemListener: OnItemListener) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {
    val dayOfMonth: TextView
    private val onItemListener: OnItemListener

    init {
        dayOfMonth = itemView.findViewById(R.id.cellDayText)
        this.onItemListener = onItemListener
        itemView.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        onItemListener.onItemClick(adapterPosition, dayOfMonth.text as String)
    }
}