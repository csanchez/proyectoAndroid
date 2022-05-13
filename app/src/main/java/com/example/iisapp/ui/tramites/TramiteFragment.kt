package com.example.iisapp.ui.tramites

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.iisapp.R
import com.example.iisapp.data.Result
import com.example.iisapp.data.model.Tramite
import com.example.iisapp.databinding.ActivityLoginBinding
import com.example.iisapp.databinding.FragmentGalleryBinding
import com.example.iisapp.databinding.FragmentTramiteBinding
import com.example.iisapp.rest.ApiClient
import com.example.iisapp.ui.gallery.GalleryViewModel
import com.example.iisapp.ui.login.LoginFormState
import com.example.iisapp.ui.login.LoginResult
import com.example.iisapp.ui.login.LoginViewModel
import com.example.iisapp.ui.login.LoginViewModelFactory
import com.example.iisapp.ui.notifications.placeholder.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
open class TramiteFragment : Fragment() {


    private lateinit var tramitesViewModel: TramitesViewModel
    private lateinit var _binding: FragmentTramiteBinding
    private val binding get() = _binding!!




    private var columnCount = 1
    open val tramiteType: String = ""

    private val tagg="TRAMITES FRAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notification_list, container, false)

        tramitesViewModel = ViewModelProvider(this,TramitesViewModelFactory()).get(TramitesViewModel::class.java)

        _binding = FragmentTramiteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        tramitesViewModel.tramitesResult.observe(viewLifecycleOwner, Observer {
            val tramitesState = it ?: return@Observer



            if (tramitesState.error != null) {
                //showLoginFailed(loginResult.error)
                Log.d(tagg, "sin tramites")
            }
            if (tramitesState.success != null) {
                Log.d(tagg, " succcess")
               // updateUiWithUser(loginResult.success)
                // Set the adapter
                if (view is RecyclerView) {
                    with(view) {
                        layoutManager = when {
                            columnCount <= 1 -> LinearLayoutManager(context)
                            else -> GridLayoutManager(context, columnCount)
                        }
                        // adapter = NotificationRecyclerViewAdapter(PlaceholderContent.ITEMS)
                        adapter = TramiteRecyclerViewAdapter(tramitesState.success)
                        adapter.apply {
                            //addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
                            var itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                            itemDecoration.setDrawable(context.getDrawable(R.drawable.divider)!!)
                            addItemDecoration(itemDecoration)
                        }


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
            TramiteFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}