package com.iis.app.ui.reservations




import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iis.app.R
import com.iis.app.data.model.Reservation


internal class CalendarDayEventsRecyclerAdapter(
    private val events: ArrayList<Reservation>
) :
    RecyclerView.Adapter<CalendarDayEventsRecyclerAdapter.CalendarDayViewHolder>() {

    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarDayViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.event_calendar_day, parent, false)
        val layoutParams = view.layoutParams
        //layoutParams.height = (parent.width * 0.15).toInt()
        context = parent.context
        return CalendarDayViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarDayViewHolder, position: Int) {
        holder.eventTextView.setText(events[position].name)
        val location = IntArray(2)
        holder.eventTextView.getLocationOnScreen(location)
        Log.d("recycler",location[0].toString()+ " "+location[1].toString())
    }

    override fun getItemCount(): Int {
        return events.count()
    }

    interface OnItemListener {
        fun onItemClick(position: Int, dayText: String?)
    }

    inner class CalendarDayViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView){
        val eventTextView: TextView

        init {
            eventTextView = itemView.findViewById(R.id.eventName)
        }


    }
}