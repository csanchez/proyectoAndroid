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

       /* initHours()
        val hoursList: RecyclerView  = binding.hoursRecyclerView
        hoursList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        hoursList.adapter = HourRecyclerView(hours) */

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





        // now bitmap holds the updated pixels

        // set bitmap as background to ImageView

        /*var image = binding.eventCanvas


        val width = 1000
        val height = 2000
        val parms = FrameLayout.LayoutParams(width, height)
        image.layoutParams = FrameLayout.LayoutParams(3000, 5000)

        //layout.layoutParams.width = 20000000;
        image.requestLayout();*/

        //image.background = BitmapDrawable(resources, bitmap)

        var drawView: ReservationCustomView = ReservationCustomView(eventsList,requireContext(),null,0);
        //var drawView: ReservationCustomView = binding.eventCanvas
        drawView.setBackgroundColor(Color.WHITE);

        var width = drawView.measuredWidth
        var height = drawView.measuredHeight
        Log.d("medidas",width.toString()+"  "+height.toString())
        //drawView.setLayoutParams(FrameLayout.LayoutParams(500, 900))

        binding.eventCanvas.addView(drawView)

        binding.root.invalidate()
        binding.eventCanvas.invalidate();
        binding.root.invalidate()

         width = drawView.measuredWidth
         height = drawView.measuredHeight
        Log.d("medidas",width.toString()+"  "+height.toString())

        /*calendarRecyclerView =  binding.eventsDayContainer
        calendarRecyclerView!!.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val calendarAdapter = CalendarDayEventsRecyclerAdapter(eventsList!!) //daysInMonth?.let { CalendarRecyclerViewAdapter(it,showDaysOfMonth!!, this) }
        calendarRecyclerView!!.adapter = calendarAdapter*/


        /*val parentLayout: FrameLayout =   binding.eventsDayContainer
        eventsList.forEach{
            Log.d("list",it.name)
            val textView = View.inflate(context, R.layout.event_calendar_day, null) as androidx.constraintlayout.widget.ConstraintLayout

            parentLayout.addView(textView);
        }*/
    }



}