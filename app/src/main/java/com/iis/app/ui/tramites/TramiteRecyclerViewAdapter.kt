package com.iis.app.ui.tramites

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ShapeDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.shape.*
import com.iis.app.R
import com.iis.app.data.model.Tramite
import com.iis.app.databinding.FragmentTramiteItemListBinding
import com.iis.app.ui.notifications.placeholder.PlaceholderContent.PlaceholderItem

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class TramiteRecyclerViewAdapter(private val values: List<Tramite>,private val tramiteType:String,private val onClickListener: TramiteRecyclerViewAdapter.OnClickListener) : RecyclerView.Adapter<TramiteRecyclerViewAdapter.TramiteViewHolder>() {


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
        val initialsTramite: TextView = binding.tramiteInitials

        //val descripcionView: TextView = binding.tramiteDescripcion



        val context = binding.root.context
        val root = binding.root

        val departmentsList: RecyclerView = binding.tramiteItemListDepartment



        fun bind(tramite: Tramite) {
            //statusView.text = tramite.status
            nameView.text = tramite.name
            initialsTramite.text  = tramite.name.uppercase()[0].toString()

           // descripcionView.text = tramite.descripcion
            departmentsList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            //departmentsList.adapter = TramiteDepartmentsRecyclerViewAdapter(tramite.departments)


            val roundedCornerTreatment = RoundedCornerTreatment()
            val shapeAppearanceModel = ShapeAppearanceModel()
                .toBuilder()
                .setAllCorners(roundedCornerTreatment)
                .setAllCornerSizes(RelativeCornerSize(0.5f))
                .build()

            val shapeDrawable =  MaterialShapeDrawable(shapeAppearanceModel) //ShapeDrawable(shapeAppearanceModel)  //


            shapeDrawable.setFillColor(ContextCompat.getColorStateList(context, R.color.status_0));
            //shapeDrawable.paint.setColor(Color.parseColor(“#FFFFFF”));

            ViewCompat.setBackground(initialsTramite,shapeDrawable);






            departmentsList.adapter = TramiteDepartmentsRecyclerViewAdapter(tramite.departments,
                TramiteDepartmentsRecyclerViewAdapter.OnClickListener { position ->
                    if(tramiteType=="personal"){
                        var action =TramitesPersonalFragmentDirections.actionNavTramitesToNavTramite( position)
                        root.findNavController().navigate( action)
                    }else{
                        var action =TramitesInstitucionalFragmentDirections.actionNavTramitesToNavTramite(position)
                        root.findNavController().navigate( action)
                    }
                }
            )
        }



        override fun toString(): String {
            return super.toString() + " '" + nameView.text + "'"
        }
    }

    class OnClickListener(val clickListener: (position: Int) -> Unit) {
        fun onClick(position: Int) = clickListener(position)
    }

}