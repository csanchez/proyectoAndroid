package com.iis.app.ui.notifications


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.iis.app.R
import com.iis.app.databinding.FragmentNotificationBinding
import com.iis.app.ui.components.TextDrawable


/**
 * A simple [Fragment] subclass.
 * Use the [NotificationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotificationFragment : Fragment() {

    private val args:    NotificationFragmentArgs by navArgs()
    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var _binding: FragmentNotificationBinding

    private val binding get() = _binding!!
    private val tagg="NOTIFICATION FRAG"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view =  inflater.inflate(R.layout.fragment_notification, container, false)


        notificationsViewModel = ViewModelProvider(requireActivity(), NotificationsViewModelFactory()).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        val root: View = binding.root



        val sharedPref = this.activity?.getSharedPreferences(getString(R.string.shared_preferences_name),
            Context.MODE_PRIVATE)
        val token = sharedPref?.getString(getString(R.string.saved_api_token),"")

        notificationsViewModel.notificationsResult.observe(viewLifecycleOwner, Observer {
            val notificationsState = it ?: return@Observer

            if (notificationsState.success != null) {
                with(view) {
                    val initialsView = view.findViewById(R.id.notification_initials) as ImageView
                    val senderView = view.findViewById(R.id.notification_sender) as TextView
                    val dateView = view.findViewById(R.id.notification_date) as TextView
                    val titleView = view.findViewById(R.id.notification_title) as TextView
                    val messageView = view.findViewById(R.id.notification_message) as TextView
                    val urlView = view.findViewById(R.id.notification_url) as TextView

                    val notification =notificationsState.success[args.notificationPosition]


                    val drawable = TextDrawable.builder().buildRound(notification.originInitials,   Color.parseColor(notification.color.trim()))
                    initialsView.setImageDrawable(drawable)


                    senderView.text = notification.sender
                    dateView.text = notification.createdAt
                    titleView.text = notification.title
                    messageView.text = notification.message
                    urlView.text = notification.url



                    notificationsViewModel.markAsSeen(args.notificationPosition, "Bearer ${token}")
                }
            }
        })




        return view
    }


}