package com.example.iisapp.ui.solicitudes

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.iisapp.data.model.Solicitud
import com.example.iisapp.data.model.Tramite
import com.example.iisapp.databinding.FragmentSolicitudItemListBinding

import com.example.iisapp.ui.placeholder.PlaceholderContent.PlaceholderItem
import com.example.iisapp.ui.tramites.TramiteDepartmentsRecyclerViewAdapter
import com.example.iisapp.ui.tramites.TramiteRecyclerViewAdapter

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class SolicitudesRecyclerViewAdapter(private val values: List<Solicitud>, private val onClickListener: TramiteRecyclerViewAdapter.OnClickListener) : RecyclerView.Adapter<SolicitudesRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

         return ViewHolder(
             FragmentSolicitudItemListBinding.inflate(
                 LayoutInflater.from(parent.context),
                 parent,
                 false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.itemView.setOnClickListener {
            onClickListener.onClick(position)
        }
        holder.bind(item)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentSolicitudItemListBinding) : RecyclerView.ViewHolder(binding.root) {

        val statusView = binding.status as TextView
        val nameView = binding.name as TextView
        val startedAtView = binding.startedAt as TextView


        //val context = binding.root.context
        //val departmentsList: RecyclerView = binding.tramiteItemListDepartment



        fun bind(solicitud: Solicitud) {
            //statusView.text = notification.status
            statusView.text = solicitud.status
            nameView.text = solicitud.tramiteName
            startedAtView.text = solicitud.startedAt
            //descripcionView.text = tramite.descripcion
            //departmentsList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            //departmentsList.adapter = TramiteDepartmentsRecyclerViewAdapter(tramite.departments)
        }


        override fun toString(): String {
            return super.toString() //+ " '" + contentView.text + "'"
        }


    }

    class OnClickListener(val clickListener: (position: Int) -> Unit) {
        fun onClick(position: Int) = clickListener(position)
    }

}