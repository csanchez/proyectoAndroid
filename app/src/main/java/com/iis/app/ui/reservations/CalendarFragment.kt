package com.iis.app.ui.reservations



import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iis.app.R
import com.iis.app.databinding.FragmentCalendarBinding
import com.iis.app.ui.solicitudes.SolicitudesFragmentDirections
import com.iis.app.ui.solicitudes.SolicitudesRecyclerViewAdapter
import com.iis.app.ui.solicitudes.SolicitudesViewModel
import com.iis.app.ui.solicitudes.SolicitudesViewModelFactory
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjuster
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

    private val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(context, 7)

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
        calendarRecyclerView!!.layoutManager = layoutManager
        //setMonthView()


        reservationsViewModel.selectedDate.observe(viewLifecycleOwner, Observer {
            val selectedDate = it ?: return@Observer
            //Toast.makeText(context, "cambio de fechas manito", Toast.LENGTH_LONG).show()
            Log.d("calednar", selectedDate.toString())
            monthYearText!!.text = monthYearFromDate(selectedDate)
            setMonthView()


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



        })
        reservationsViewModel.initDate()


        return root
    }




    //private fun setMonthView() {
    private fun setMonthView() {

        Log.d("calednar", "setMonthView")



        val daysInMonth     =  reservationsViewModel.daysInMonthArray()
        val showDaysOfMonth =  reservationsViewModel.showDayArray()
        val calendarAdapter = CalendarRecyclerViewAdapter(daysInMonth!!,showDaysOfMonth!!, this) //daysInMonth?.let { CalendarRecyclerViewAdapter(it,showDaysOfMonth!!, this) }
        calendarRecyclerView!!.adapter = calendarAdapter




        /*selectedDate?.let {

            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-d")


            val start =  it.with( TemporalAdjusters.firstDayOfMonth()).format(formatter)
            val end   =  it.with( TemporalAdjusters.lastDayOfMonth()).format(formatter)
            val space ="all"
            val user = "all"
            val event = "all"
            val service = "all"
            val requireEquipo =  "false"


            reservationsViewModel.getReservations(start,end,space,user,event,service,requireEquipo,"Bearer ${token}")


            monthYearText!!.text = monthYearFromDate(it)
            val daysInMonth = daysInMonthArray(it)
            val calendarAdapter = daysInMonth?.let { it1 -> CalendarRecyclerViewAdapter(it1, this) }

            reservationsViewModel.reservationsResult.observe(viewLifecycleOwner, Observer {
                val reservationsState = it ?: return@Observer

                if (reservationsState.error != null) {
                    //showLoginFailed(loginResult.error)

                    Log.d("Calendari","error")
                    calendarRecyclerView!!.adapter = calendarAdapter
                }
                if (reservationsState.success != null) {

                    Log.d("Calendari","sucess")
                    // updateUiWithUser(loginResult.success)
                    // Set the adapter

                    /*if(solicitudesState.success.size == 0){
                        list.visibility = View.GONE
                        binding.zeroSolicitudes.visibility = View.VISIBLE
                    }else{
                        list.visibility = View.VISIBLE
                        binding.zeroSolicitudes.visibility = View.GONE
                    }*/









                }

            })





        }*/

    }




    /* private fun daysInMonthArray(date: LocalDate): ArrayList<String>? {
        val daysInMonthArray: ArrayList<String> = ArrayList()
        val yearMonth: YearMonth = YearMonth.from(date)
        val daysInMonth: Int = yearMonth.lengthOfMonth()
        val firstOfMonth = selectedDate!!.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value
        for (i in 1..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("")
            } else {
                daysInMonthArray.add((i - dayOfWeek).toString())
            }
        }
        return daysInMonthArray
    } */

    private fun monthYearFromDate(date: LocalDate): String? {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date.format(formatter)
    }

    fun previousMonthAction() {
        //selectedDate = selectedDate!!.minusMonths(1)
        //setMonthView()
        reservationsViewModel.previousMonthAction()
    }

    fun nextMonthAction() {
        //selectedDate = selectedDate!!.plusMonths(1)
        //setMonthView()
        reservationsViewModel.nextMonthAction()
    }

    override fun onItemClick(position: Int, dayText: String?) {

        if (dayText != "") {
            //val message = "Selected Date " + dayText + " " + monthYearFromDate(selectedDate!!)
            //Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }


}