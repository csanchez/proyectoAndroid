package com.iis.app.ui.reservations


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iis.app.R
import com.iis.app.data.model.IisNotification
import com.iis.app.databinding.CalendarCellBinding
import com.iis.app.databinding.FragmentCalendarBinding
import com.iis.app.ui.notifications.NotificationRecyclerViewAdapter


//class CalendarRecyclerViewAdapter(private val daysOfMonth: ArrayList<String>, private val onItemListener: OnItemListener) : RecyclerView.Adapter<CalendarRecyclerViewAdapter.CalendarViewHolder>() {


class CalendarRecyclerViewAdapter(private val daysOfMonth: ArrayList<String>, private val onItemListener: CalendarRecyclerViewAdapter.OnClickListener) : RecyclerView.Adapter<CalendarRecyclerViewAdapter.CalendarViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
       /* val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.calendar_cell, parent, false)
        val layoutParams = view.layoutParams
        layoutParams.height = (parent.height * 0.166666666).toInt()
        return CalendarViewHolder(view, onItemListener)*/

        return CalendarViewHolder(
            CalendarCellBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    /*override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.dayOfMonth.setText(daysOfMonth[position])
    }*/

    override fun onBindViewHolder(holder: CalendarRecyclerViewAdapter.CalendarViewHolder, position: Int) {
        val item = daysOfMonth[position]


        /*holder.itemView.setOnClickListener {
            onClickListener.onClick(position)
        }*/

        holder.bind(item)


    }

    override fun getItemCount(): Int {
        return daysOfMonth.size
    }

    /*interface OnItemListener {
        fun onItemClick(position: Int, dayText: String?)
    }*/

    //inner class CalendarViewHolder(itemView: View, onItemListener: OnItemListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    inner class CalendarViewHolder(binding: CalendarCellBinding) :  RecyclerView.ViewHolder(binding.root) {
        val dayOfMonth: TextView = binding.cellDayText

        fun bind(dayOfMonthValue: String) {
            dayOfMonth.text = dayOfMonthValue

        }


        //private val onItemListener: OnItemListener

        /*init {
            dayOfMonth = itemView.findViewById(R.id.cellDayText)
            this.onItemListener = onItemListener
            itemView.setOnClickListener(this)
        }*/

        /*override fun onClick(view: View) {
            onItemListener.onItemClick(adapterPosition, dayOfMonth.text as String)
        }*/


    }

    class OnClickListener(val clickListener: (position: Int) -> Unit) {
        fun onClick(position: Int) = clickListener(position)
    }

}