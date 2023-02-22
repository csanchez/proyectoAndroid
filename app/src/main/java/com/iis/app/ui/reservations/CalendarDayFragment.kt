package com.iis.app.ui.reservations


import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iis.app.databinding.FragmentCalendarDayBinding
import java.time.LocalDate


class CalendarDayFragment: Fragment() {

    private lateinit var reservationsViewModel: ReservationsViewModel
    private lateinit var _binding: FragmentCalendarDayBinding
    private val binding get() = _binding!!

    private val args: CalendarDayFragmentArgs by navArgs()

    var hours: ArrayList<String> = ArrayList(26)

    private var calendarRecyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        reservationsViewModel = ViewModelProvider(requireActivity(), ReservationsViewModelFactory()).get(
            ReservationsViewModel::class.java)

        _binding = FragmentCalendarDayBinding.inflate(inflater, container, false)
        val root: View = binding.root



        val daySelected = args.dayNumber

        val selectedDate = reservationsViewModel.getSelectedDate()?.withDayOfMonth(daySelected)

        binding.selectedDayTextView.text = selectedDate.toString()


        if (selectedDate != null) {
            filterEvents(selectedDate)
        }
        return root

    }


    private fun initHours(){
        for (hour in 8..20){
            hours.add( String.format("%02d:00", hour)   )
            hours.add( String.format("%02d:30", hour)   )
        }
    }

    private fun filterEvents(selectedDate: LocalDate){
        val eventsList = reservationsViewModel.filterEvents(selectedDate)




        var drawView: ReservationCustomView = ReservationCustomView(eventsList,requireContext(),null,0);

        //var drawView: ReservationCustomView = binding.eventCanvas
        //drawView.setBackgroundColor(Color.WHITE);

        binding.eventCanvas.addView(drawView)

       /* var width = drawView.measuredWidth
        var height = drawView.measuredHeight
        Log.d("medidas",width.toString()+"  "+height.toString())
        //drawView.setLayoutParams(FrameLayout.LayoutParams(500, 900))



        binding.root.invalidate()
        binding.eventCanvas.invalidate();
        binding.root.invalidate()

         width = drawView.measuredWidth
         height = drawView.measuredHeight
        Log.d("medidas",width.toString()+"  "+height.toString()) */


    }



}