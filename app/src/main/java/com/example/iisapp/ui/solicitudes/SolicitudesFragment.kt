package com.example.iisapp.ui.solicitudes

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.iisapp.R
import com.example.iisapp.databinding.FragmentTramiteItemListBinding
import com.example.iisapp.ui.placeholder.PlaceholderContent
import com.example.iisapp.ui.tramites.TramiteRecyclerViewAdapter
import com.example.iisapp.ui.tramites.TramitesInstitucionalFragmentDirections
import com.example.iisapp.ui.tramites.TramitesPersonalFragmentDirections

/**
 * A fragment representing a list of Items.
 */
class SolicitudesFragment : Fragment() {

    private lateinit var solicitudesViewModel: SolicitudesViewModel
    private lateinit var _binding: FragmentTramiteItemListBinding
    private val binding get() = _binding!!

    private val tagg="SOLICITUDES FRAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_solicitudes_list, container, false)


        solicitudesViewModel = ViewModelProvider(requireActivity(), SolicitudesViewModelFactory()).get(
            SolicitudesViewModel::class.java)

        _binding = FragmentTramiteItemListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        solicitudesViewModel.solicitudesResult.observe(viewLifecycleOwner, Observer {
            val solicitudesState = it ?: return@Observer

            if (solicitudesState.error != null) {
                //showLoginFailed(loginResult.error)
                Log.d(tagg, "sin tramites")
            }
            if (solicitudesState.success != null) {
                Log.d(tagg, " succcess")
                // updateUiWithUser(loginResult.success)
                // Set the adapter
                if (view is RecyclerView) {
                    with(view) {
                        /*layoutManager = when {
                            columnCount <= 1 -> LinearLayoutManager(context)
                            else -> GridLayoutManager(context, columnCount)
                        }*/
                        // adapter = NotificationRecyclerViewAdapter(PlaceholderContent.ITEMS)
                        adapter = SolicitudesRecyclerViewAdapter(solicitudesState.success,
                            TramiteRecyclerViewAdapter.OnClickListener { position ->
                                Toast.makeText(context, "Click en solicitud", Toast.LENGTH_SHORT).show()
                                //val intent = Intent(context, TramiteActivity::class.java)
                                //intent.putExtra("position", position)
                                //startActivity(intent)

                                /*if(tramiteType=="personal"){
                                    var action = TramitesPersonalFragmentDirections.actionNavTramitesToNavTramite( position)
                                    view.findNavController().navigate( action)
                                }else{
                                    var action = TramitesInstitucionalFragmentDirections.actionNavTramitesToNavTramite(position)
                                    view.findNavController().navigate( action)
                                }*/


                            }
                        )


                    }
                }

            }

        })
        val sharedPref = this.activity?.getSharedPreferences(getString(R.string.shared_preferences_name),
            Context.MODE_PRIVATE)
        val token = sharedPref?.getString(getString(R.string.saved_api_token),"")
        solicitudesViewModel.getSolicitudes("Bearer ${token}")

        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
                SolicitudesFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}