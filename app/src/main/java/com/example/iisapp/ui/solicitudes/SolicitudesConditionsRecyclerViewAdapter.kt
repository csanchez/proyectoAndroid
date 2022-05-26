package com.example.iisapp.ui.solicitudes

import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.iisapp.R
import com.example.iisapp.data.model.Condition
import com.example.iisapp.databinding.FragmentSolicitudConditionBinding
import com.example.iisapp.ui.tramites.TramiteDepartmentsRecyclerViewAdapter


class SolicitudesConditionsRecyclerViewAdapter(private val values: List<Condition>) : RecyclerView.Adapter<SolicitudesConditionsRecyclerViewAdapter.ViewHolder>() {

    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            FragmentSolicitudConditionBinding.inflate(
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


    inner class ViewHolder(binding: FragmentSolicitudConditionBinding) : RecyclerView.ViewHolder(binding.root) {

        val instructionsView: TextView = binding.solicitudConditionInstruction
        val iisRoleView: TextView = binding.solicitudConditionIisRole
        val completedAtView: TextView = binding.solicitudConditionCompletedAt
        val linearLayoutView: LinearLayout = binding.solicitudConditionLinearLayout
        val titleView: TextView = binding.solicitudConditionTitle
        val leftBorderView: View = binding.solicitudConditionLeftBorderTitle
        val rigthBorderView: View = binding.solicitudConditionRigthBorderTitle






        fun bind(condition: Condition) {

            instructionsView.text = condition.instruction
            val iisRoleViewHeight = iisRoleView.height

            if(condition.completed){
                iisRoleView.visibility = View.GONE
                linearLayoutView.setBackgroundResource(R.drawable.condition_success_topless_border)
                titleView.text = "Completado"
                val color = ContextCompat.getColor(context!!, R.color.success)
                titleView.setTextColor(  color )//
                leftBorderView.setBackgroundColor(color)
                rigthBorderView.setBackgroundColor(color)
            }else{
                completedAtView.visibility = View.GONE
                iisRoleView.text = "Esperando respuesta de: ${condition.iisRole}"
                linearLayoutView.setBackgroundResource(R.drawable.condition_not_success_topless_border)
                titleView.text = "Sin Completar"
                val color = ContextCompat.getColor(context!!, R.color.not_success)
                titleView.setTextColor(  color )//
                leftBorderView.setBackgroundColor(color)
                rigthBorderView.setBackgroundColor(color)

            }

        }



        override fun toString(): String {
            return super.toString()// + " '" + nameView.text + "'"
        }
    }


}