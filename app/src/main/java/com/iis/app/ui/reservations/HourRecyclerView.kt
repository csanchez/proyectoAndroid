package com.iis.app.ui.reservations

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iis.app.databinding.HourCellBinding


class HourRecyclerView(private val values: ArrayList<String>) : RecyclerView.Adapter<HourRecyclerView.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            HourCellBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = values.size



    inner class ViewHolder(binding: HourCellBinding) : RecyclerView.ViewHolder(binding.root) {
        val labelView  = binding.hourText as TextView

        fun bind(time: String) {
            labelView.text = time
        }

        override fun toString(): String {
            return super.toString()
        }
    }

}