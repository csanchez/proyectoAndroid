package com.iis.app.ui.tramites

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iis.app.databinding.FragmentTramiteBinding
import com.iis.app.R
import com.iis.app.ui.components.TextViewWithTitle


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
    private val tagg="TRAMITES FRAG"


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

            val sharedPref = this.activity?.getSharedPreferences(getString(R.string.shared_preferences_name),
                Context.MODE_PRIVATE)
            val token = sharedPref?.getString(getString(R.string.saved_api_token),"")

            tramitesViewModel.tramitesResult.observe(viewLifecycleOwner, Observer {
                val tramitesState = it ?: return@Observer



                if (tramitesState.success != null) {
                        with(view) {
                            val tramiteName = view.findViewById(R.id.tramiteName) as TextViewWithTitle
                            val tramiteDescripcion = view.findViewById(R.id.tramiteDescripcion) as TextViewWithTitle
                            val tramiteInstructions = view.findViewById(R.id.tramiteInstructions) as TextViewWithTitle
                            val tramite =tramitesState.success[args.tramitePosition]
                            val dataList = view.findViewById(R.id.tramiteData) as RecyclerView




                            tramiteName.setBody(tramite.name)
                            tramiteDescripcion.setBody(tramite.descripcion)
                            tramiteInstructions.setBody(tramite.instructions)

                            dataList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
                            dataList.adapter = TramiteDataRecyclerViewAdapter(tramite.data)

                            val registerToTramite = view.findViewById(R.id.registerToTramite) as Button

                            registerToTramite.setOnClickListener {
                                //loading.visibility = View.VISIBLE
                                Toast.makeText(requireActivity().applicationContext, "Registrando al tramite", Toast.LENGTH_SHORT).show()



                                tramitesViewModel.registerToTramite(tramite,"Bearer ${token}")

                                //val userCredentials = UserCredentials(username.text.toString(), password.text.toString(),getDeviceId(), getDeviceName(),getFCMToken())
                                //loginViewModel.login(userCredentials)
                            }

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