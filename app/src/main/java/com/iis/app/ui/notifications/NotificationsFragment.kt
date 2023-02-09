package com.iis.app.ui.notifications

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.iis.app.R
import com.iis.app.databinding.FragmentNotificationListBinding


/**
 * A fragment representing a list of Items.
 */
class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var _binding: FragmentNotificationListBinding
    private val binding get() = _binding!!


    private val tagg="NOTIFICATIONS FRAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // val view = inflater.inflate(R.layout.fragment_notification_list, container, false)

        notificationsViewModel = ViewModelProvider(requireActivity(), NotificationsViewModelFactory()).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val list: RecyclerView =  binding.notificationsList//view.findViewById(R.id.notifications_list)



        if (notificationsViewModel.notificationsResult.value == null) {
            val sharedPref = this.activity?.getSharedPreferences(getString(R.string.shared_preferences_name),
                Context.MODE_PRIVATE)
            val token = sharedPref?.getString(getString(R.string.saved_api_token),"")

            notificationsViewModel.getNotifications("Bearer ${token}")
        }




        notificationsViewModel.notificationsResult.observe(viewLifecycleOwner, Observer {
            val notificationsState = it ?: return@Observer



            if (notificationsState.error != null) {
                //showLoginFailed(loginResult.error)
            }
            if (notificationsState.success != null) {

                if(notificationsState.success.size == 0){
                    list.visibility = View.GONE
                    binding.zeroNotificaciones.visibility = View.VISIBLE
                }else{
                    list.visibility = View.VISIBLE
                    binding.zeroNotificaciones.visibility = View.GONE
                }


                if (list is RecyclerView) {
                    with(list) {

                        adapter = NotificationRecyclerViewAdapter(notificationsState.success,
                            NotificationRecyclerViewAdapter.OnClickListener { position ->

                                notificationsState.success[position]

                                var action =  NotificationsFragmentDirections.actionNavNotificationsToNavNotification(position)
                                root.findNavController().navigate(action)
                            }
                        )


                        val divider = DividerItemDecoration(context,DividerItemDecoration.VERTICAL)
                        divider.setDrawable(ContextCompat.getDrawable(context,R.drawable.divider)!!)

                        list.addItemDecoration(divider)

                    }
                }

            }

        })



        return root
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