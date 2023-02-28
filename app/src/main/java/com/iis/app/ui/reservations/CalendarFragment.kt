package com.iis.app.ui.reservations



import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iis.app.R
import com.iis.app.databinding.FragmentCalendarBinding
import com.iis.app.ui.LoadingView

import com.iis.app.ui.LoadingViewSingleton
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters


// create calendar https://github.com/codeWithCal/CalendarTutorialAndroidStudio/blob/master/app/src/main/res/layout/activity_main.xml


// https://www.youtube.com/watch?v=Ba0Q-cK1fJo
class CalendarFragment : Fragment(), CalendarRecyclerViewAdapter.OnItemListener  {



    private lateinit var reservationsViewModel: ReservationsViewModel
    private lateinit var _binding: FragmentCalendarBinding
    private val binding get() = _binding!!

    //private lateinit var  calendar:CalendarView

    private var monthYearText: TextView? = null
    private var calendarRecyclerView: RecyclerView? = null




    //private var selectedDate: LocalDate? = null

    //private val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(context, 7)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        reservationsViewModel = ViewModelProvider(requireActivity(), ReservationsViewModelFactory()).get(
            ReservationsViewModel::class.java)

        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.prevtMonth.setOnClickListener {
            previousMonthAction()
        }
        binding.nextMonth.setOnClickListener {
            nextMonthAction()
        }

        //selectedDate = LocalDate.now();

        monthYearText =  binding.monthYearTV
        calendarRecyclerView =  binding.calendarRecyclerView
        calendarRecyclerView!!.layoutManager = GridLayoutManager(context, 7)
        //setMonthView()



        reservationsViewModel.selectedDate.observe(viewLifecycleOwner, Observer {
            val selectedDate = it ?: return@Observer



            //Toast.makeText(context, "cambio de fechas manito", Toast.LENGTH_LONG).show()
            Log.d("calednar", selectedDate.toString())
            monthYearText!!.text = monthYearFromDate(selectedDate)
            //setMonthView()


            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-d")
            val start =  selectedDate.with( TemporalAdjusters.firstDayOfMonth()).format(formatter)
            val end   =  selectedDate.with( TemporalAdjusters.lastDayOfMonth()).format(formatter)
            val space ="all"
            val user = "all"
            val event = "all"
            val service = "all"
            val requireEquipo =  "false"

            val sharedPref = this.activity?.getSharedPreferences(getString(R.string.shared_preferences_name),
                Context.MODE_PRIVATE)
            val token = sharedPref?.getString(getString(R.string.saved_api_token),"")

            reservationsViewModel.getReservations(start,end,space,user,event,service,requireEquipo,"Bearer ${token}")

        })


        reservationsViewModel.reservationsResult.observe(viewLifecycleOwner, Observer {
            val reservationsState = it ?: return@Observer

            if (reservationsState.error != null) {
                Log.d("Calendari","error")
            }
            if (reservationsState.success != null) {
                Log.d("Calendari","sucess")
                setMonthView()

            }
            setMonthView()


            val handler = Handler()
            //handler.postDelayed({ requireActivity().findViewById<LoadingView?>(R.id.contentLoading).visibility = View.GONE }, 1000)
            handler.postDelayed({ LoadingViewSingleton.hide() }, LoadingViewSingleton.time)


        })
        reservationsViewModel.initDate()


        return root
    }


    override fun onResume() {
        super.onResume()
        //requireActivity().findViewById<LoadingView?>(R.id.contentLoading).visibility = View.VISIBLE
        LoadingViewSingleton.show()


    }

    //private fun setMonthView() {
    private fun setMonthView() {

        Log.d("calednar", "setMonthView")



        val daysInMonth     =  reservationsViewModel.daysInMonthArray()
        val showDaysOfMonth =  reservationsViewModel.showDayArray()
        val calendarAdapter = CalendarRecyclerViewAdapter(daysInMonth!!,showDaysOfMonth!!, this) //daysInMonth?.let { CalendarRecyclerViewAdapter(it,showDaysOfMonth!!, this) }
        calendarRecyclerView!!.adapter = calendarAdapter





    }





    private fun monthYearFromDate(date: LocalDate): String? {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date.format(formatter)
    }

    fun previousMonthAction() {
        LoadingViewSingleton.show()
        reservationsViewModel.previousMonthAction()
    }

    fun nextMonthAction() {
        LoadingViewSingleton.show()
        reservationsViewModel.nextMonthAction()
    }

    override fun onItemClick(position: Int, dayText: String?) {


        if( !LoadingViewSingleton.isShow()) {
            dayText?.let {
                if (it != "") {
                    var action =
                        CalendarFragmentDirections.actionNavCalendarToNavCalendarDay(it.toInt())
                    binding.root.findNavController().navigate(action)

                }
            }
        }


    }


}