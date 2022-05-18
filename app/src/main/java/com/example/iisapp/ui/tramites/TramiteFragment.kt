package com.example.iisapp.ui.tramites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.iisapp.databinding.FragmentTramiteBinding
import com.example.iisapp.R


/**
 * A simple [Fragment] subclass.
 * Use the [TramiteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TramiteFragment : Fragment() {

    private val args: TramiteFragmentArgs by navArgs()
    private lateinit var tramitesViewModel: TramitesViewModel
    private lateinit var _binding: FragmentTramiteBinding
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_tramite, container, false)


        tramitesViewModel = ViewModelProvider(requireActivity(),TramitesViewModelFactory()).get(TramitesViewModel::class.java)

        _binding = FragmentTramiteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        tramitesViewModel.tramitesResult.observe(viewLifecycleOwner, Observer {
            val tramitesState = it ?: return@Observer


            if (tramitesState.success != null) {
                Log.d(tag, " succcess")
                    with(view) {
                        val name = view.findViewById(R.id.tramiteName) as TextView
                        val tramite =tramitesState.success[args.tramitePosition]
                       // tramitesViewModel.getTramite(args.tramitePosition)
                        name.text = tramite.name

                    }

            }
        })

        //val name = view.findViewById(R.id.tramiteName) as TextView
        //val tramite = tramitesViewModel.getTramite(args.tramitePosition)
        //name.text = tramite.name
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TramiteFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TramiteFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}