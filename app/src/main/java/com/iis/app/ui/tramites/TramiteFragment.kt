package com.iis.app.ui.tramites

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iis.app.R
import com.iis.app.data.model.Tramite
import com.iis.app.databinding.FragmentTramiteBinding


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





    //inflate the menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
       // inflater.inflate(R.menu.navigation, menu)
        //hide some items from this fragment (e.g. sort)
        menu!!.findItem(R.id.action_add_solicitud).isVisible = true
        super.onCreateOptionsMenu(menu, inflater)

    }


    //handle item clicks of menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //get item id to handle item clicks
        val id = item!!.itemId
        //handle item clicks
        if (id == R.id.action_add_solicitud){
            tramitesViewModel.tramitesResult.observe(viewLifecycleOwner, Observer {
                val tramitesState = it ?: return@Observer

                if (tramitesState.success != null) {
                    val sharedPref = this.activity?.getSharedPreferences(getString(R.string.shared_preferences_name),
                        Context.MODE_PRIVATE)
                    val token = sharedPref?.getString(getString(R.string.saved_api_token),"")
                    val tramite =tramitesState.success[args.tramitePosition]
                    tramitesViewModel.registerToTramite(tramite,"Bearer ${token}")

                    tramitesViewModel.tramiteRegisteredResult.observe(viewLifecycleOwner, Observer {
                        val tramitesState = it ?: return@Observer

                        if (tramitesState.success != null) {
                            Toast.makeText(context, "Solicitud registrada", Toast.LENGTH_SHORT).show()
                            var action =  TramiteFragmentDirections.actionNavTramiteToNavSolicitud(-1)
                            findNavController().navigate(action)
                        }else{
                            Toast.makeText(context, "Error al registrar la solicitud", Toast.LENGTH_SHORT).show()
                        }


                    })
                }

            })

        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

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

           // val sharedPref = this.activity?.getSharedPreferences(getString(R.string.shared_preferences_name), Context.MODE_PRIVATE)
            //val token = sharedPref?.getString(getString(R.string.saved_api_token),"")

            tramitesViewModel.tramitesResult.observe(viewLifecycleOwner, Observer {
                val tramitesState = it ?: return@Observer



                if (tramitesState.success != null) {
                        with(view) {
                            val tramiteName = view.findViewById(R.id.tramiteName) as TextView //WithTitle
                            val tramiteDescripcion = view.findViewById(R.id.tramiteDescripcion) as TextView //WithTitle
                            val tramiteInstructions = view.findViewById(R.id.tramiteInstructions) as TextView //WithTitle
                            val tramite =tramitesState.success[args.tramitePosition]
                            val dataList = view.findViewById(R.id.tramiteData) as RecyclerView




                            tramiteName.text = tramite.name
                            tramiteDescripcion.text = tramite.descripcion
                            tramiteInstructions.text = tramite.instructions

                            dataList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
                            dataList.adapter = TramiteDataRecyclerViewAdapter(tramite.data)

                            /*val registerToTramite = view.findViewById(R.id.registerToTramite) as Button

                            registerToTramite.setOnClickListener {
                                //loading.visibility = View.VISIBLE
                                Toast.makeText(requireActivity().applicationContext, "Registrando al tramite", Toast.LENGTH_SHORT).show()



                                tramitesViewModel.registerToTramite(tramite,"Bearer ${token}")

                                //val userCredentials = UserCredentials(username.text.toString(), password.text.toString(),getDeviceId(), getDeviceName(),getFCMToken())
                                //loginViewModel.login(userCredentials)
                            }*/

                        }

                }
            })



            //val name = view.findViewById(R.id.tramiteName) as TextView
            //val tramite = tramitesViewModel.getTramite(args.tramitePosition)
            //name.text = tramite.name
            return view
        }

}