package com.example.iisapp.ui.tramites

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.iisapp.R
import com.example.iisapp.databinding.FragmentTramiteItemListBinding

/**
 * A fragment representing a list of Items.
 */
open class  TramitesFragment : Fragment() {


    private lateinit var tramitesViewModel: TramitesViewModel
    private lateinit var _binding: FragmentTramiteItemListBinding
    private val binding get() = _binding!!




    open val tramiteType: String = ""

    private val tagg="TRAMITES FRAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tramite_list, container, false)

        tramitesViewModel = ViewModelProvider(requireActivity(),TramitesViewModelFactory()).get(TramitesViewModel::class.java)

        _binding = FragmentTramiteItemListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        tramitesViewModel.tramitesResult.observe(viewLifecycleOwner, Observer {
            val tramitesState = it ?: return@Observer

            if (tramitesState.error != null) {
                Log.d(tagg, "sin tramites")
            }
            if (tramitesState.success != null) {
                Log.d(tagg, " succcess")

                if (view is RecyclerView) {
                    with(view) {

                        Log.d(tagg, " mostrara tramites")
                        adapter = TramiteRecyclerViewAdapter(tramitesState.success,
                            TramiteRecyclerViewAdapter.OnClickListener { position ->
                                if(tramiteType=="personal"){
                                    var action =TramitesPersonalFragmentDirections.actionNavTramitesToNavTramite( position)
                                    view.findNavController().navigate( action)
                                }else{
                                    var action =TramitesInstitucionalFragmentDirections.actionNavTramitesToNavTramite(position)
                                    view.findNavController().navigate( action)
                                }
                            }
                        )
                    }
                }

            }
        })

        val sharedPref = this.activity?.getSharedPreferences(getString(R.string.shared_preferences_name),Context.MODE_PRIVATE)
        val token = sharedPref?.getString(getString(R.string.saved_api_token),"")

        Log.d(tagg, "token ${token}")


        tramitesViewModel.getTramites(this.tramiteType,"Bearer ${token}")

        return view
    }



    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            TramitesFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}