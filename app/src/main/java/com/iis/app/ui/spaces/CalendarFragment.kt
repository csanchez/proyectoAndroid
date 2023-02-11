package com.iis.app.ui.spaces

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.iis.app.R
import com.iis.app.databinding.FragmentCalendarBinding


import android.widget.CalendarView
import android.widget.Toast

// create calendar https://github.com/codeWithCal/CalendarTutorialAndroidStudio/blob/master/app/src/main/res/layout/activity_main.xml
// https://www.youtube.com/watch?v=Ba0Q-cK1fJo
class CalendarFragment : Fragment() {

    private lateinit var _binding: FragmentCalendarBinding
    private val binding get() = _binding!!

    private lateinit var  calendar:CalendarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // solicitudesViewModel = ViewModelProvider(requireActivity(), SolicitudesViewModelFactory()).get(
          //  SolicitudesViewModel::class.java)

        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //calendar = binding.calendarView


        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // Note that months are indexed from 0. So, 0 means January, 1 means february, 2 means march etc.
            val msg = "Selected date is " + dayOfMonth + "/" + (month + 1) + "/" + year
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
        return root
    }


}