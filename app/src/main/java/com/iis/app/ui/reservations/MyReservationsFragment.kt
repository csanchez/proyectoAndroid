package com.iis.app.ui.reservations

import android.content.Context
import android.os.Bundle
import android.os.Handler
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
import com.iis.app.databinding.FragmentMyReservationsListBinding
import com.iis.app.ui.LoadingViewSingleton
import com.iis.app.ui.notifications.NotificationRecyclerViewAdapter
import com.iis.app.ui.notifications.NotificationsFragmentDirections
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.*


/**
 * A fragment representing a list of Items.
 */
class MyReservationsFragment : Fragment() {

    private var columnCount = 1

    private lateinit var reservationsViewModel: ReservationsViewModel
    private lateinit var _binding: FragmentMyReservationsListBinding
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        reservationsViewModel = ViewModelProvider(requireActivity(), ReservationsViewModelFactory()).get(
            ReservationsViewModel::class.java)

        _binding = FragmentMyReservationsListBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val list: RecyclerView =  binding.myReservations

        reservationsViewModel.reservationsResult.observe(viewLifecycleOwner, Observer {
            val reservationsState = it ?: return@Observer

            if (reservationsState.error != null) {
                Log.d("Calendari","error")
            }
            if (reservationsState.success != null) {

                if(reservationsState.success.size == 0){
                    list.visibility = View.GONE
                    binding.zeroReservations.visibility = View.VISIBLE
                }else{
                    list.visibility = View.VISIBLE
                    binding.zeroReservations.visibility = View.GONE
                }

                with(list) {

                    adapter = MyReservationsRecyclerViewAdapter(reservationsState.success,
                        MyReservationsRecyclerViewAdapter.OnClickListener { position ->
                            if( !LoadingViewSingleton.isShow()) {

                                var action =
                                    MyReservationsFragmentDirections.actionNavMyReservtionsToNavReservation(
                                        position
                                    )
                                root.findNavController().navigate(action)
                            }
                        }
                    )


                    val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                    divider.setDrawable(ContextCompat.getDrawable(context,R.drawable.divider)!!)

                    list.addItemDecoration(divider)

                }

            }


            val handler = Handler()
            handler.postDelayed({ LoadingViewSingleton.hide() }, LoadingViewSingleton.time)


        })


        val sharedPref = this.activity?.getSharedPreferences(getString(R.string.shared_preferences_name),
            Context.MODE_PRIVATE)
        val token = sharedPref?.getString(getString(R.string.saved_api_token),"")

        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val start =  "$currentYear-01-01"
        val end   =  "$currentYear-12-31"
        val space ="all"
        val user = sharedPref?.getString(getString(R.string.saved_rfc),"")
        val event = "all"
        val service = "all"
        val requireEquipo =  "false"




        if (user != null) {
            reservationsViewModel.getReservations(start,end,space,user,event,service,requireEquipo,"Bearer ${token}")
        }

        return root
    }


}