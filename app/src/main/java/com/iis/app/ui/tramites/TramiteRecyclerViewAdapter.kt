package com.iis.app.ui.tramites

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iis.app.data.model.Tramite
import com.iis.app.databinding.FragmentTramiteItemListBinding
import com.iis.app.ui.notifications.placeholder.PlaceholderContent.PlaceholderItem

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class TramiteRecyclerViewAdapter(private val values: List<Tramite>,private val onClickListener: OnClickListener) : RecyclerView.Adapter<TramiteRecyclerViewAdapter.TramiteViewHolder>() {

    /* private var values = listOf<IisNotification>()
         set(value) {
             field = value
             notifyDataSetChanged()
         }*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TramiteViewHolder {

        return TramiteViewHolder(
            FragmentTramiteItemListBinding.inflate(
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

        holder.itemView.setOnClickListener {
            onClickListener.onClick(position)
        }
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

    inner class TramiteViewHolder(binding: FragmentTramiteItemListBinding) : RecyclerView.ViewHolder(binding.root) {

        //val statusView: TextView = binding.tramiteStatus
        val nameView: TextView = binding.tramiteName
        val descripcionView: TextView = binding.tramiteDescripcion

        val context = binding.root.context
        val departmentsList: RecyclerView = binding.tramiteItemListDepartment



        fun bind(tramite: Tramite) {
            //statusView.text = tramite.status
            nameView.text = tramite.name
            descripcionView.text = tramite.descripcion
            departmentsList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            departmentsList.adapter = TramiteDepartmentsRecyclerViewAdapter(tramite.departments)
        }



        override fun toString(): String {
            return super.toString() + " '" + nameView.text + "'"
        }
    }

    class OnClickListener(val clickListener: (position: Int) -> Unit) {
        fun onClick(position: Int) = clickListener(position)
    }

}