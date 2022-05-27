package com.example.iisapp.ui.notifications

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.iisapp.R
import com.example.iisapp.data.model.IisNotification
import com.example.iisapp.databinding.FragmentNotificationItemListBinding
import com.example.iisapp.ui.solicitudes.SolicitudesFragmentDirections
import com.example.iisapp.ui.solicitudes.SolicitudesRecyclerViewAdapter

/**
 * A fragment representing a list of Items.
 */
class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var _binding: FragmentNotificationItemListBinding
    private val binding get() = _binding!!


    private val tagg="NOTIFICATIONS FRAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notification_list, container, false)

        notificationsViewModel = ViewModelProvider(requireActivity(), NotificationsViewModelFactory()).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationItemListBinding.inflate(inflater, container, false)
        val root: View = binding.root


        // Set the adapter
        /*if (view is RecyclerView) {
            with(view) {

                Log.d(tagg,"Nostrara notificaciones")
                Log.d(tagg,"${IisNotification.data}")
                adapter = NotificationRecyclerViewAdapter(IisNotification.data,
                    NotificationRecyclerViewAdapter.OnClickListener { position ->
                        var action =  NotificationsFragmentDirections.actionNavNotificationsToNavNotification(position)
                        view.findNavController().navigate(action)


                    }
                )
                adapter.apply {
                    //addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
                    var itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                    itemDecoration.setDrawable(context.getDrawable(R.drawable.divider)!!)
                    addItemDecoration(itemDecoration)
                }
            }
        }*/

        notificationsViewModel.notificationsResult.observe(viewLifecycleOwner, Observer {
            val notificationsState = it ?: return@Observer

            if (notificationsState.error != null) {
                //showLoginFailed(loginResult.error)
                Log.d(tagg, "sin tramites")
            }
            if (notificationsState.success != null) {
                Log.d(tagg, " succcess")
                if (view is RecyclerView) {
                    with(view) {
                        adapter = NotificationRecyclerViewAdapter(notificationsState.success,
                            NotificationRecyclerViewAdapter.OnClickListener { position ->
                                var action =  NotificationsFragmentDirections.actionNavNotificationsToNavNotification(position)
                                view.findNavController().navigate(action)
                            }
                        )
                    }
                }

            }

        })


        val sharedPref = this.activity?.getSharedPreferences(getString(R.string.shared_preferences_name),
            Context.MODE_PRIVATE)
        val token = sharedPref?.getString(getString(R.string.saved_api_token),"")

        notificationsViewModel.getNotifications("Bearer ${token}")
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            NotificationsFragment().apply {
                arguments = Bundle().apply {
                    //putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}