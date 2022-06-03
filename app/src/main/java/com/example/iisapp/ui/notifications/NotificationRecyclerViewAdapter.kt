package com.example.iisapp.ui.notifications

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.iisapp.R
import com.example.iisapp.data.model.IisNotification
import com.example.iisapp.databinding.FragmentNotificationItemListBinding

import com.example.iisapp.ui.notifications.placeholder.PlaceholderContent.PlaceholderItem
import com.example.iisapp.ui.solicitudes.SolicitudesRecyclerViewAdapter

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class NotificationRecyclerViewAdapter(private val values: List<IisNotification>, private val onClickListener: NotificationRecyclerViewAdapter.OnClickListener) : RecyclerView.Adapter<NotificationRecyclerViewAdapter.ViewHolder>() {

    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            FragmentNotificationItemListBinding.inflate(
                LayoutInflater.from(parent .context),
                parent,
                false
            )
        )

    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]


        holder.itemView.setOnClickListener {
            onClickListener.onClick(position)
        }

        holder.bind(item)


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

    inner class ViewHolder(binding: FragmentNotificationItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val notificationTypeView: TextView = binding.notificationType
        val senderView: TextView = binding.notificationSender
        val titleView: TextView = binding.notificationTitle
        val messageView: TextView = binding.notificationMessage
       // val dateView: TextView = binding.date

        fun bind(notification: IisNotification) {

            titleView.text = notification.title
            messageView.text = notification.message
            senderView.text = notification.sender
            //notificationTypeView.text = notification.notificationType


            val drawableAnnouncement = context?.let { ContextCompat.getDrawable(it, R.drawable.ic_announcement) }
            val drwableMessage = context?.let { ContextCompat.getDrawable(it, R.drawable.ic_message) }
            val drwableNote = context?.let { ContextCompat.getDrawable(it, R.drawable.ic_note) }
            val drwableLabel = context?.let { ContextCompat.getDrawable(it, R.drawable.ic_label_important) }

            val drawable = when (notification.notificationType) {
                "alerta" ->         notificationTypeView.setCompoundDrawablesWithIntrinsicBounds(drawableAnnouncement, null, null, null)
                "notificacion" ->   notificationTypeView.setCompoundDrawablesWithIntrinsicBounds(drwableMessage, null, null, null)
                "aviso" ->          notificationTypeView.setCompoundDrawablesWithIntrinsicBounds(drwableNote, null, null, null)
                "comunicado" ->     notificationTypeView.setCompoundDrawablesWithIntrinsicBounds(drwableLabel, null, null, null)
                else -> { // Note the block

                }
            }
           // val drawable = context?.let { ContextCompat.getDrawable(it, R.drawable.ic_announcement) }



            /*if(notification.status == "unseen") {
                statusView.text = "nuevo"
            }else{
                statusView.text = "visto"
            }*/





        }



        override fun toString(): String {
            return super.toString() + " '" + senderView.text + "'"
        }
    }

    class OnClickListener(val clickListener: (position: Int) -> Unit) {
        fun onClick(position: Int) = clickListener(position)
    }

}