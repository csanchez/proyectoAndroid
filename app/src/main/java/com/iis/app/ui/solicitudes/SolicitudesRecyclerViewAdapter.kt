package com.iis.app.ui.solicitudes

import android.content.Context
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.iis.app.R
import com.iis.app.data.model.Solicitud
import com.iis.app.databinding.FragmentSolicitudItemListBinding
import com.iis.app.ui.components.TextDrawable

import com.iis.app.ui.placeholder.PlaceholderContent.PlaceholderItem

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class SolicitudesRecyclerViewAdapter(private val values: List<Solicitud>, private val onClickListener: SolicitudesRecyclerViewAdapter.OnClickListener) : RecyclerView.Adapter<SolicitudesRecyclerViewAdapter.ViewHolder>() {


    var context: Context? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
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
        val numSolView = binding.numSol as TextView
        val initialsView: ImageView = binding.solicitudInitials
        //val progressBar = binding.progressBar as ProgressBar



        fun bind(solicitud: Solicitud) {
            //statusView.text = notification.status
            statusView.text = solicitud.statusName
            nameView.text = solicitud.tramiteName
            startedAtView.text = solicitud.startedAt
            numSolView.text = "Solicitud # "+solicitud.tramiteUserNumber

            val drawable = TextDrawable.builder().buildRound(solicitud.tramiteName.uppercase()[0].toString(),   Color.parseColor(solicitud.color.trim()) )
            initialsView.setImageDrawable(drawable)

            context?.   let {
                val radius = it.resources.getDimension(R.dimen.status_corner_radius)
                val shapeAppearanceModel = ShapeAppearanceModel()
                    .toBuilder()
                    .setAllCorners(CornerFamily.ROUNDED, radius.toFloat())
                    .build()

                val shapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)

                when (solicitud.status) {
                    "0" -> shapeDrawable.setFillColor(
                        ContextCompat.getColorStateList(it,
                            R.color.status_0));
                    "1" -> shapeDrawable.setFillColor(
                        ContextCompat.getColorStateList(it,
                            R.color.status_1));
                    "2" -> shapeDrawable.setFillColor(
                        ContextCompat.getColorStateList(it,
                            R.color.status_2));
                    "3" -> shapeDrawable.setFillColor(
                        ContextCompat.getColorStateList(it,
                            R.color.status_3));
                    "4" -> shapeDrawable.setFillColor(
                        ContextCompat.getColorStateList(it,
                            R.color.status_4));
                    else -> { // Note the block
                        shapeDrawable.setFillColor(ContextCompat.getColorStateList(it, R.color.status_0));
                    }

                }
                // shapeDrawable.setStroke(2.0f, ContextCompat.getColor(this,R.color....));
                ViewCompat.setBackground(statusView, shapeDrawable)

            }




            //progressBar.progress = solicitud.progress

        }


        override fun toString(): String {
            return super.toString() //+ " '" + contentView.text + "'"
        }


    }

    class OnClickListener(val clickListener: (position: Int) -> Unit) {
        fun onClick(position: Int) = clickListener(position)
    }

}