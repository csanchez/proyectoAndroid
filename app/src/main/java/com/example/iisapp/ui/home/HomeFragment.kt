package com.example.iisapp.ui.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.iisapp.R
import com.example.iisapp.databinding.FragmentHomeBinding


//https://www.androidhive.info/2017/02/android-creating-gmail-like-inbox-using-recyclerview/
class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null


    private val tagg="HOME FRAG"

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        homeViewModel = ViewModelProvider(requireActivity(), HomeViewModelFactory()).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root



        Log.d("NOME", "EN HONE")



        /*val myWebView: WebView = root.findViewById(R.id.home_web_view)
        myWebView.loadUrl("https://www.iis.unam.mx/")
        */

        //val view =  inflater.inflate(R.layout.fragment_home, container, false)

        val list: RecyclerView = root.findViewById(R.id.news_list)

       // if (homeViewModel.newsResult.value == null) {
            Log.d(tagg, "Sin noticias")
            val sharedPref = this.activity?.getSharedPreferences(getString(R.string.shared_preferences_name),
                Context.MODE_PRIVATE)
            val token = sharedPref?.getString(getString(R.string.saved_api_token),"")

            homeViewModel.getNews("Bearer ${token}")
       // }


        homeViewModel.newsResult.observe(viewLifecycleOwner, Observer {
            val homeState = it ?: return@Observer



            if (homeState.error != null) {
                //showLoginFailed(loginResult.error)
                Log.d(tagg, "error en news")
            }
            if (homeState.success != null) {
                Log.d(tagg, " con news")
                //if (list is RecyclerView) {
                    with(list) {


                        adapter = HomeRecyclerViewAdapter(homeState.success,
                            HomeRecyclerViewAdapter.OnClickListener { position ->

                                var new = homeState.success[position]
                                val i = Intent(Intent.ACTION_VIEW)
                                i.data = Uri.parse(new.url)
                                startActivity(i)

                                //var action =  NotificationsFragmentDirections.actionNavNotificationsToNavNotification(position)
                                //view.findNavController().navigate(action)
                            }
                        )


                        /*val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                        divider.setDrawable(ContextCompat.getDrawable(context,R.drawable.divider)!!)

                        list.addItemDecoration(divider)*/

                    }
                //}

            }

        })






        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}