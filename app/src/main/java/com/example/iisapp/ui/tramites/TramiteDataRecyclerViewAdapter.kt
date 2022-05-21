package com.example.iisapp.ui.tramites

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.iisapp.data.model.Data
import com.example.iisapp.data.model.Department
import com.example.iisapp.databinding.FragmentTramiteDataBinding
import com.example.iisapp.databinding.FragmentTramiteItemListDepartmentBinding

class TramiteDataRecyclerViewAdapter(private val values: List<Data>) : RecyclerView.Adapter<TramiteDataRecyclerViewAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentTramiteDataBinding.inflate(
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
        holder.setIsRecyclable(false) //Para listas pequeñas
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

    inner class ViewHolder(binding: FragmentTramiteDataBinding) : RecyclerView.ViewHolder(binding.root) {

        val labelView  = binding.tramiteDataLabel as TextView
        val valueView  = binding.tramiteDataValue as EditText



        fun bind(data: Data) {
            labelView.text = data.label
            valueView.hint = "Introduce el valor de "+data.label

            valueView.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {}

                override fun beforeTextChanged(s: CharSequence, start: Int,
                                               count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence, start: Int,before: Int, count: Int) {
                    Log.d("DATA RECYCLER", " cambio un data")
                    data.value = s.toString()
                }

            })





        }



        override fun toString(): String {
            return super.toString()// + " '" + nameView.text + "'"
        }
    }

}