package com.iis.app.ui.reservations



import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import com.iis.app.R
import com.iis.app.databinding.CalendarCellBinding

 /*
class CalendarRecyclerViewAdapter(private val daysOfMonth: ArrayList<String>, private val onItemListener: CalendarRecyclerViewAdapter.OnClickListener) : RecyclerView.Adapter<CalendarRecyclerViewAdapter.CalendarViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {


        return CalendarViewHolder(
            CalendarCellBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }



    override fun onBindViewHolder(holder: CalendarRecyclerViewAdapter.CalendarViewHolder, position: Int) {
        val item = daysOfMonth[position]




        holder.bind(item)


    }

    override fun getItemCount(): Int {
        return daysOfMonth.count()
    }



    inner class CalendarViewHolder(binding: CalendarCellBinding) :  RecyclerView.ViewHolder(binding.root) {
        val dayOfMonth: TextView = binding.cellDayText

        fun bind(dayOfMonthValue: String) {
            dayOfMonth.text = dayOfMonthValue

        }




    }

    class OnClickListener(val clickListener: (position: Int) -> Unit) {
        fun onClick(position: Int) = clickListener(position)
    }

}*/




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
        layoutParams.height = (parent.height * 0.166666666).toInt()
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
                if(day == daysOfMonth[position])
                    hasEvents = true
            }

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
            onItemListener.onItemClick(adapterPosition, dayOfMonth.text as String)
        }
    }
}