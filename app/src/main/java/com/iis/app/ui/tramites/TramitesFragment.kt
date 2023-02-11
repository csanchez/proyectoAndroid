package com.iis.app.ui.tramites

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.iis.app.R
import com.iis.app.databinding.FragmentTramiteListBinding


/**
 * A fragment representing a list of Items.
 */
open class  TramitesFragment : Fragment() {


    private lateinit var tramitesViewModel: TramitesViewModel
    private lateinit var _binding: FragmentTramiteListBinding
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
        //val view = inflater.inflate(R.layout.fragment_tramite_list, container, false)

        tramitesViewModel = ViewModelProvider(requireActivity(),TramitesViewModelFactory()).get(TramitesViewModel::class.java)

        _binding =  FragmentTramiteListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val list: RecyclerView =  binding.tramitesList   //view.findViewById(R.id.tramites_list)



        //if (tramitesViewModel.tramitesResult.value == null) {
            val sharedPref = this.activity?.getSharedPreferences(getString(R.string.shared_preferences_name),Context.MODE_PRIVATE)
            val token = sharedPref?.getString(getString(R.string.saved_api_token),"")
            tramitesViewModel.getTramites(this.tramiteType,"Bearer ${token}")
        //
        //
        // }

        tramitesViewModel.tramitesResult.observe(viewLifecycleOwner, Observer {
            val tramitesState = it ?: return@Observer

            if (tramitesState.error != null) {
            }
            if (tramitesState.success != null) {




                if(tramitesState.success.size == 0){
                    list.visibility = View.GONE
                    binding.zeroTramites.visibility = View.VISIBLE
                }else{
                    list.visibility = View.VISIBLE
                    binding.zeroTramites.visibility = View.GONE
                }

                if (list is RecyclerView) {
                    with(list) {

                        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                        divider.setDrawable(ContextCompat.getDrawable(context,R.drawable.divider)!!)

                        list.addItemDecoration(divider)


                        adapter = TramiteRecyclerViewAdapter(tramitesState.success,tramiteType,
                            TramiteRecyclerViewAdapter.OnClickListener { position ->
                                if(tramiteType=="personal"){
                                    var action =TramitesPersonalFragmentDirections.actionNavTramitesToNavTramite( position)
                                    root.findNavController().navigate( action)
                                }else{
                                    var action =TramitesInstitucionalFragmentDirections.actionNavTramitesToNavTramite(position)
                                    root.findNavController().navigate( action)
                                }
                            }
                        )
                    }
                }


            }
        })



        return root  //view
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