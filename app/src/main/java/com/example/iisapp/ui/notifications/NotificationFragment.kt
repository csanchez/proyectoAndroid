package com.example.iisapp.ui.notifications


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.iisapp.R
import com.example.iisapp.databinding.FragmentNotificationBinding


/**
 * A simple [Fragment] subclass.
 * Use the [NotificationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotificationFragment : Fragment() {

    private val args: NotificationFragmentArgs by navArgs()
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

        Log.d(tagg,"Mostrando notificacion 1")

        val sharedPref = this.activity?.getSharedPreferences(getString(R.string.shared_preferences_name),
            Context.MODE_PRIVATE)
        val token = sharedPref?.getString(getString(R.string.saved_api_token),"")

        notificationsViewModel.notificationsResult.observe(viewLifecycleOwner, Observer {
            val notificationsState = it ?: return@Observer

            Log.d(tagg,"Mostrando notificacion")
            if (notificationsState.success != null) {
                with(view) {
                    val initialsView = view.findViewById(R.id.notification_initials) as TextView
                    val senderView = view.findViewById(R.id.notification_sender) as TextView
                    val dateView = view.findViewById(R.id.notification_date) as TextView
                    val titleView = view.findViewById(R.id.notification_title) as TextView
                    val messageView = view.findViewById(R.id.notification_message) as TextView
                    val urlView = view.findViewById(R.id.notification_url) as TextView

                    val notification =notificationsState.success[args.notificationPosition]

                    Log.d(tagg,"Mostrando notificacion $notification")

                    initialsView.text = notification.originInitials
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NotificationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NotificationFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}