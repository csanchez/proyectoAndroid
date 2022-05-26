package com.example.iisapp.ui.solicitudes

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iisapp.R
import com.example.iisapp.databinding.FragmentSolicitudBinding

import com.example.iisapp.ui.components.TextViewWithTitle
import com.example.iisapp.ui.tramites.TramiteDataRecyclerViewAdapter


/**
 * A simple [Fragment] subclass.
 * Use the [SolicitudFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SolicitudFragment : Fragment() {


    private val args: SolicitudFragmentArgs by navArgs()
    private lateinit var solicitudViewModel: SolicitudesViewModel
    private lateinit var _binding: FragmentSolicitudBinding

    private val binding get() = _binding!!
    private val tagg="TRAMITES FRAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_solicitud, container, false)


        solicitudViewModel = ViewModelProvider(requireActivity(), SolicitudesViewModelFactory()).get(SolicitudesViewModel::class.java)

        _binding = FragmentSolicitudBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val sharedPref = this.activity?.getSharedPreferences(getString(R.string.shared_preferences_name),
            Context.MODE_PRIVATE)
        val token = sharedPref?.getString(getString(R.string.saved_api_token),"")

        solicitudViewModel.solicitudesResult.observe(viewLifecycleOwner, Observer {
            val solicitudesState = it ?: return@Observer


            if (solicitudesState.success != null) {
                with(view) {
                    val tramiteNameView = view.findViewById(R.id.tramiteName) as TextViewWithTitle
                    val solicitudNameView = view.findViewById(R.id.solicitud_name) as TextViewWithTitle
                    val solicitudDescriptionView = view.findViewById(R.id.solicitud_description) as TextViewWithTitle
                    val dataList = view.findViewById(R.id.solicitud_conditions_list) as RecyclerView

                    val solicitud =solicitudesState.success[args.solicitudPosition]
                    val currentStep = solicitud.currentStep
                    val conditions = currentStep.conditions

                    tramiteNameView.setBody(solicitud.tramiteName)

                    solicitudNameView.setBody(currentStep.name)
                    solicitudDescriptionView.setBody(currentStep.descripcion)





                    dataList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
                    dataList.adapter = SolicitudesConditionsRecyclerViewAdapter(conditions)

                }
            }
        })



        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SolicitudFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SolicitudFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}