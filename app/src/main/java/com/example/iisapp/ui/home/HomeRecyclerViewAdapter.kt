package com.example.iisapp.ui.home




import android.content.Context
import android.graphics.Color
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import coil.load
import com.example.iisapp.R
import com.example.iisapp.data.model.IisNotification
import com.example.iisapp.data.model.New
import com.example.iisapp.databinding.FragmentHomeBinding
import com.example.iisapp.databinding.FragmentHomeItemListBinding


import com.example.iisapp.ui.notifications.placeholder.PlaceholderContent.PlaceholderItem


/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class HomeRecyclerViewAdapter(private val values: List<New>, private val onClickListener: HomeRecyclerViewAdapter.OnClickListener) : RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder>() {

    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            FragmentHomeItemListBinding.inflate(
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

    inner class ViewHolder(binding: FragmentHomeItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //val titleView: TextView = binding.homeNewTitle
        val imageView: ImageView = binding.homeNewImage
        val layout: LinearLayout = binding.homeNewLayout

        fun bind(new: New) {
            Log.d("HomeRecyclerViewAdapter",new.toString())
            Log.d("HomeRecyclerViewAdapter",new.title)
            //titleView.text = new.title
            imageView.load(new.imageUrl)
            layout.setBackgroundColor(0x333333)
        }



        override fun toString(): String {
            return super.toString() //+ " '" + titleView.text + "'"
        }
    }

    class OnClickListener(val clickListener: (position: Int) -> Unit) {
        fun onClick(position: Int) = clickListener(position)
    }

}