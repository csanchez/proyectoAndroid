package com.example.iisapp.ui.tramites

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.iisapp.R
import com.example.iisapp.data.model.Tramite

import com.example.iisapp.ui.notifications.placeholder.PlaceholderContent.PlaceholderItem
import com.example.iisapp.databinding.FragmentTramiteBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class TramiteRecyclerViewAdapter(

    private val values: List<Tramite>



) : RecyclerView.Adapter<TramiteRecyclerViewAdapter.TramiteViewHolder>() {

    /* private var values = listOf<IisNotification>()
         set(value) {
             field = value
             notifyDataSetChanged()
         }*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TramiteViewHolder {

        return TramiteViewHolder(
            FragmentTramiteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    /*override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        holder.idView.text = item.id
        holder.contentView.text = item.content

    }*/

    override fun onBindViewHolder(holder: TramiteViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)

        /*if(item.status=="seen"){
            holder.statusView.visibility = View.INVISIBLE
        }*/


    }

    override fun getItemCount(): Int = values.size

    /*inner class ViewHolder(binding: FragmentNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }*/

    inner class TramiteViewHolder(binding: FragmentTramiteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val statusView: TextView = binding.status
        val nameView: TextView = binding.name
        val descripcionView: TextView = binding.descripcion
        // val dateView: TextView = binding.date

        fun bind(tramite: Tramite) {
            //statusView.text = notification.status
            statusView.text = tramite.status
            nameView.text = tramite.name
            descripcionView.text = tramite.descripcion



        }



        override fun toString(): String {
            return super.toString() + " '" + nameView.text + "'"
        }
    }

}