package com.iis.app.ui.notifications

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.iis.app.R
import com.iis.app.data.model.IisNotification
import com.iis.app.databinding.FragmentNotificationItemListBinding
import com.iis.app.ui.notifications.placeholder.PlaceholderContent.PlaceholderItem



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

    inner class ViewHolder(binding: FragmentNotificationItemListBinding) : RecyclerView.ViewHolder(binding.root) {

        //val notificationTypeView: TextView = binding.notificationType
        val initialsView: TextView = binding.notificationInitials
        val senderView: TextView = binding.notificationSender
        val titleView: TextView = binding.notificationTitle
        //val messageView: TextView = binding.notificationMessage
       // val statusView: TextView = binding.notificationStatus
       // val mainLayout: LinearLayout = binding.notificationMainLayout
       val dateView: TextView = binding.notificationDate



        fun bind(notification: IisNotification) {
            initialsView.text = notification.originInitials
            titleView.text = notification.title
            //messageView.text = notification.message.substring(0,  100)+" ..."
            senderView.text = notification.sender
            dateView.text = notification.createdAt
            //statusView.text = notification.status
            //notificationTypeView.text = notification.notificationType

            /*
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
            */



          /* if(notification.status == "unseen") {
               context?.resources?.let { it -> mainLayout.setBackgroundColor(it.getColor(R.color.unread)) }
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