package com.iis.app.ui.reservations

import android.content.Context
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.shape.MaterialShapeDrawable
import com.iis.app.R
import com.iis.app.databinding.FragmentReservationBinding


/**
 * A simple [Fragment] subclass.
 * Use the [ReservationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReservationFragment : Fragment() {

    private val args: ReservationFragmentArgs by navArgs()
    private lateinit var reservationsViewModel: ReservationsViewModel
    private lateinit var _binding: FragmentReservationBinding

    private val binding get() = _binding!!
    private val tagg="NOTIFICATION FRAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        reservationsViewModel = ViewModelProvider(requireActivity(), ReservationsViewModelFactory()).get(ReservationsViewModel::class.java)

        _binding = FragmentReservationBinding.inflate(inflater, container, false)
        val root: View = binding.root



        val sharedPref = this.activity?.getSharedPreferences(getString(R.string.shared_preferences_name),
            Context.MODE_PRIVATE)
        val token = sharedPref?.getString(getString(R.string.saved_api_token),"")

        reservationsViewModel.reservationsResult.observe(viewLifecycleOwner, Observer {
            val reservationsState = it ?: return@Observer

            if (reservationsState.success != null) {
                val initialsView: TextView = binding.reservationInitials
                val sapceView: TextView = binding.reservationSpace
                val dateView: TextView = binding.reservationDate
                val nameView: TextView = binding.reservationName

                val reservation =reservationsState.success[args.reservationPosition]

                initialsView.text = reservation.spaceInitials
                sapceView.text = reservation.spaceName
                dateView.text = reservation.startDate+" "+reservation.startTime+" - "+reservation.endTime
                nameView.text = reservation.name

                //Log.d("reservation", ContextCompat.getColorStateList(requireContext(), R.color.status_0).toString())



            }

        })
        return root
    }


}