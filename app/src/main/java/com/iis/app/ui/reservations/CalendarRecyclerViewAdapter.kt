package com.iis.app.ui.reservations



import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iis.app.R





internal class CalendarRecyclerViewAdapter(
    private val daysOfMonth: ArrayList<String>,
    private val showDaysOfMonth: List<String>,
    private val onItemListener: OnItemListener
) :
    RecyclerView.Adapter<CalendarRecyclerViewAdapter.CalendarViewHolder>() {

    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.calendar_cell, parent, false)
        val layoutParams = view.layoutParams
        layoutParams.height = (parent.height * 0.15).toInt()
        context = parent.context
        return CalendarViewHolder(view, onItemListener)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.dayOfMonth.setText(daysOfMonth[position])



        if(showDaysOfMonth.count() == 0){
            holder.cellDayDot.visibility = View.INVISIBLE
        }else{

            var hasEvents = false

            for (day in showDaysOfMonth) {
                if(daysOfMonth[position].isNotEmpty())
                    if (day.toInt() == daysOfMonth[position].toInt())
                        hasEvents = true

            }
            Log.d("CALEnDARIO","DIA DEL MES "+ daysOfMonth[position]+" HAS EVENTS "+hasEvents.toString())
            if( hasEvents){
                holder.cellDayDot.visibility = View.VISIBLE
            }else{
                holder.cellDayDot.visibility = View.INVISIBLE
            }
        }




    }

    override fun getItemCount(): Int {
        return daysOfMonth.count()
    }

    interface OnItemListener {
        fun onItemClick(position: Int, dayText: String?)
    }

   inner class CalendarViewHolder(itemView: View, onItemListener: OnItemListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val dayOfMonth: TextView
        val cellDayDot: RelativeLayout

        private val onItemListener: OnItemListener

        init {
            dayOfMonth = itemView.findViewById(R.id.cellDayText)
            cellDayDot = itemView.findViewById(R.id.cellDayDot)

            this.onItemListener = onItemListener
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            Log.d("onClick",view.toString())
            onItemListener.onItemClick(adapterPosition, dayOfMonth.text as String)
        }
    }
}