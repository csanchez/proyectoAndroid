package com.example.iisapp.ui.notifications

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
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

        val list: RecyclerView = view.findViewById(R.id.notifications_list)
        //val progressBar: ProgressBar = view.findViewById(R.id.app_progress_bar)
        //progressBar.visibility=View.VISIBLE
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


        if (notificationsViewModel.notificationsResult.value == null) {
            Log.d(tagg, "Sin notificaciones")
            val sharedPref = this.activity?.getSharedPreferences(getString(R.string.shared_preferences_name),
                Context.MODE_PRIVATE)
            val token = sharedPref?.getString(getString(R.string.saved_api_token),"")

            notificationsViewModel.getNotifications("Bearer ${token}")
        }




        notificationsViewModel.notificationsResult.observe(viewLifecycleOwner, Observer {
            val notificationsState = it ?: return@Observer



            if (notificationsState.error != null) {
                //showLoginFailed(loginResult.error)
                Log.d(tagg, "error en notificaciones")
            }
            if (notificationsState.success != null) {
                Log.d(tagg, " con notificaciones")
                if (list is RecyclerView) {
                    with(list) {

                        /*Handler().postDelayed({
                            // This method will be executed once the timer is over
                            // Start your app main activity

                            progressBar.visibility=View.GONE

                            adapter = NotificationRecyclerViewAdapter(notificationsState.success,
                                NotificationRecyclerViewAdapter.OnClickListener { position ->
                                    var action =  NotificationsFragmentDirections.actionNavNotificationsToNavNotification(position)
                                    view.findNavController().navigate(action)
                                }
                            )
                        }, 1000)
*/

                        //progressBar.visibility=View.GONE

                        adapter = NotificationRecyclerViewAdapter(notificationsState.success,
                            NotificationRecyclerViewAdapter.OnClickListener { position ->

                                notificationsState.success[position]

                                var action =  NotificationsFragmentDirections.actionNavNotificationsToNavNotification(position)
                                view.findNavController().navigate(action)
                            }
                        )


                        val divider = DividerItemDecoration(context,DividerItemDecoration.VERTICAL)
                        divider.setDrawable(ContextCompat.getDrawable(context,R.drawable.divider)!!)

                        list.addItemDecoration(divider)

                    }
                }

            }

        })



        return view
    }


    override fun onStop() {
        super.onStop()
        Log.d(tagg, "On STOP")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tagg, "onDestroy")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(tagg, "onDestroyView")
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