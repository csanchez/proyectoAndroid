package com.example.iisapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.iisapp.R
import com.example.iisapp.databinding.FragmentHomeBinding
import org.w3c.dom.Text
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

        //val textView: TextView = binding.textHome

        val senderView: TextView = binding.sender
        val dateView: TextView = binding.date
        val titleView: TextView = binding.title
        val messageView: TextView = binding.message
        val initialsView: TextView = binding.initials


       /* homeViewModel.text  .observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/

        homeViewModel.notification.observe(viewLifecycleOwner, Observer {
            senderView.text = it.sender
            dateView.text = it.date
            titleView.text = it.title
            messageView.text = it.message
            initialsView.text = it.initials
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}