package com.example.iisapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.iisapp.R
import com.example.iisapp.databinding.FragmentHomeBinding


//https://www.androidhive.info/2017/02/android-creating-gmail-like-inbox-using-recyclerview/
class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        Log.d("NOME", "EN HONE")

        val myWebView: WebView = root.findViewById(R.id.home_web_view)
        myWebView.loadUrl("https://www.iis.unam.mx/")





        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}