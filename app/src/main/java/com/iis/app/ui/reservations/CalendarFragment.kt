package com.iis.app.ui.reservations



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.iis.app.R

import com.iis.app.databinding.FragmentCalendarBinding


import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


// create calendar https://github.com/codeWithCal/CalendarTutorialAndroidStudio/blob/master/app/src/main/res/layout/activity_main.xml
// https://www.youtube.com/watch?v=Ba0Q-cK1fJo
class CalendarFragment : Fragment()  {

    private lateinit var reservationsViewModel: ReservationsViewModel
    private lateinit var _binding: FragmentCalendarBinding
    private val binding get() = _binding!!

    //private lateinit var  calendar:CalendarView

    private var monthYearText: TextView? = null
    private var calendarRecyclerView: RecyclerView? = null
    private var selectedDate: LocalDate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.prevtMonth.setOnClickListener {
            previousMonthAction()
        }
        binding.nextMonth.setOnClickListener {
            nextMonthAction()
        }
        selectedDate = LocalDate.now();
        monthYearText =  binding.monthYearTV






        setMonthView()


        return root
    }

    private fun setMonthView() {
        selectedDate?.let { monthYearText?.text =  monthYearFromDate(it) }

        selectedDate?.let {

            val daysInMonth: ArrayList<String> = daysInMonthArray(it)!!
            //val calendarAdapter = CalendarRecyclerViewAdapter(daysInMonth, this)
            //val layoutManager: RecyclerView.LayoutManager =   GridLayoutManager(context, 7)
            //calendarRecyclerView!!.layoutManager = layoutManager
           // calendarRecyclerView!!.adapter = calendarAdapter
            calendarRecyclerView =  binding.calendarRecyclerView

            with(calendarRecyclerView) {



                this?.adapter = CalendarRecyclerViewAdapter(daysInMonth,
                    CalendarRecyclerViewAdapter.OnClickListener { position ->
                        Toast.makeText(context, "Click en una celda", Toast.LENGTH_SHORT).show()


                        //var action =  SolicitudesFragmentDirections.actionNavSolicitudesToNavSolicitud(position)
                        //root.findNavController().navigate(action)


                    }
                )

            }


            /*reservationsViewModel.reservationsResult.observe(viewLifecycleOwner, Observer {
                val reservationsState = it ?: return@Observer



                if (reservationsState.error != null) {
                    //showLoginFailed(loginResult.error)
                }
                if (reservationsState.success != null) {




                    //if (list is RecyclerView) {
                        with(calendarRecyclerView) {



                            adapter = CalendarRecyclerViewAdapter(daysInMonth,
                                CalendarRecyclerViewAdapter.OnClickListener { position ->
                                    Toast.makeText(context, "Click en una celda", Toast.LENGTH_SHORT).show()


                                    //var action =  SolicitudesFragmentDirections.actionNavSolicitudesToNavSolicitud(position)
                                    //root.findNavController().navigate(action)


                                }
                            )

                        }
                    //}

                }

            })*/


        }

       /* val daysInMonth: ArrayList<String> = selectedDate?.let { daysInMonthArray(it) }!!
        val calendarAdapter = CalendarAdapter(daysInMonth, this)
        val layoutManager: RecyclerView.LayoutManager =   GridLayoutManager(context, 7)
        calendarRecyclerView!!.layoutManager = layoutManager
        calendarRecyclerView!!.adapter = calendarAdapter*/
    }


    private fun daysInMonthArray(date: LocalDate): ArrayList<String>? {
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
    }

    private fun monthYearFromDate(date: LocalDate): String? {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date.format(formatter)
    }

    fun previousMonthAction() {
        selectedDate = selectedDate!!.minusMonths(1)
        setMonthView()
    }

    fun nextMonthAction() {
        selectedDate = selectedDate!!.plusMonths(1)
        setMonthView()
    }



}