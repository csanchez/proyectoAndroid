package com.iis.app.ui.solicitudes

import android.content.Context
import android.os.Bundle
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
import com.iis.app.databinding.FragmentTramiteItemListBinding

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

        val list: RecyclerView = view.findViewById(R.id.solicitudes_list)


        if (solicitudesViewModel.solicitudesResult.value == null) {
            val sharedPref = this.activity?.getSharedPreferences(getString(R.string.shared_preferences_name),
                Context.MODE_PRIVATE)
            val token = sharedPref?.getString(getString(R.string.saved_api_token),"")
            solicitudesViewModel.getSolicitudes("Bearer ${token}")
        }

        solicitudesViewModel.solicitudesResult.observe(viewLifecycleOwner, Observer {
            val solicitudesState = it ?: return@Observer

            if (solicitudesState.error != null) {
                //showLoginFailed(loginResult.error)
            }
            if (solicitudesState.success != null) {
                // updateUiWithUser(loginResult.success)
                // Set the adapter
                if (list is RecyclerView) {
                    with(list) {
                        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                        divider.setDrawable(ContextCompat.getDrawable(context,R.drawable.divider)!!)

                        list.addItemDecoration(divider)

                        adapter = SolicitudesRecyclerViewAdapter(solicitudesState.success,
                            SolicitudesRecyclerViewAdapter.OnClickListener { position ->
                                //Toast.makeText(context, "Click en solicitud", Toast.LENGTH_SHORT).show()


                                var action =  SolicitudesFragmentDirections.actionNavSolicitudesToNavSolicitud(position)
                                list.findNavController().navigate(action)


                            }
                        )


                    }
                }

            }

        })


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