package com.example.iisapp.ui.notifications

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.iisapp.R
import com.example.iisapp.data.model.IisNotification

import com.example.iisapp.ui.notifications.placeholder.PlaceholderContent.PlaceholderItem
import com.example.iisapp.databinding.FragmentNotificationBinding//  .ui.notifications.databinding.FragmentNotificationBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class NotificationRecyclerViewAdapter(
    //private val values: List<PlaceholderItem>
    private val values: List<IisNotification>



) : RecyclerView.Adapter<NotificationRecyclerViewAdapter.NotificationViewHolder>() {

   /* private var values = listOf<IisNotification>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {

        return NotificationViewHolder(
            FragmentNotificationBinding.inflate(
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

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)

        if(item.status=="seen"){
            holder.statusView.visibility = View.INVISIBLE
        }


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

    inner class NotificationViewHolder(binding: FragmentNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val statusView: TextView = binding.status
        val senderView: TextView = binding.sender
        val titleView: TextView = binding.title
        val messageView: TextView = binding.message
       // val dateView: TextView = binding.date

        fun bind(notification: IisNotification) {
            //statusView.text = notification.status
            titleView.text = notification.title
            messageView.text = notification.message
            senderView.text = notification.sender
         //   dateView.text = notification.date


        }



        override fun toString(): String {
            return super.toString() + " '" + senderView.text + "'"
        }
    }

}