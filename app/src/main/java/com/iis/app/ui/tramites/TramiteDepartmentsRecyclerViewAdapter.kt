package com.iis.app.ui.tramites

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iis.app.data.model.Department
import com.iis.app.databinding.FragmentTramiteItemListDepartmentBinding

class TramiteDepartmentsRecyclerViewAdapter(private val values: List<Department>) : RecyclerView.Adapter<TramiteDepartmentsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentTramiteItemListDepartmentBinding.inflate(
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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

    inner class ViewHolder(binding: FragmentTramiteItemListDepartmentBinding) : RecyclerView.ViewHolder(binding.root) {

        /*val statusView: TextView = binding.status

        val descripcionView: TextView = binding.descripcion
        // val dateView: TextView = binding.date
*/       val nameView: TextView = binding.name

        fun bind(department: Department) {
            //statusView.text = notification.status
            nameView.text = department.name
           /* statusView.text = tramite.status
            nameView.text = tramite.name
            descripcionView.text = tramite.descripcion*/
        }



        override fun toString(): String {
            return super.toString()// + " '" + nameView.text + "'"
        }
    }
}