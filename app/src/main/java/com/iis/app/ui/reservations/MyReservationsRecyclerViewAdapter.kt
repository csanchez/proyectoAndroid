package com.iis.app.ui.reservations

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iis.app.data.model.Reservation
import com.iis.app.databinding.FragmentMyReservationsListItemBinding
import com.iis.app.ui.components.TextDrawable
import com.iis.app.ui.reservations.placeholder.PlaceholderContent.PlaceholderItem


/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyReservationsRecyclerViewAdapter(
    private val values: List<Reservation>, private val onClickListener: OnClickListener
) : RecyclerView.Adapter<MyReservationsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentMyReservationsListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reservation = values[position]
        //holder.reservationInitials.text = reservation.spaceInitials
        holder.reservationName.text = reservation.name
        holder.reservationSpaceAndDate.text = reservation.spaceName+": "+reservation.startDate+ " "+reservation.startTime

        val drawable = TextDrawable.builder().buildRound(reservation.spaceInitials, Color.parseColor(reservation.color.trim()))
        holder.reservationInitials.setImageDrawable(drawable)



        holder.itemView.setOnClickListener {
            onClickListener.onClick(position)
        }



    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentMyReservationsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        //val reservationInitials: TextView = binding.reservationInitials
        val reservationName: TextView = binding.reservationName
        val reservationSpaceAndDate: TextView = binding.reservationSpaceAndDate

        val reservationInitials: ImageView = binding.initialsView


    }

    class OnClickListener(val clickListener: (position: Int) -> Unit) {
        fun onClick(position: Int) = clickListener(position)
    }

}