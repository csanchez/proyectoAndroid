package com.example.iisapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.iisapp.databinding.ActivityLoginBinding
import com.example.iisapp.databinding.ActivityTramiteBinding
import com.example.iisapp.databinding.FragmentTramiteItemListBinding
import com.example.iisapp.ui.tramites.TramitesViewModel
import com.example.iisapp.ui.tramites.TramitesViewModelFactory

class TramiteActivity : AppCompatActivity() {

    private lateinit var tramitesViewModel: TramitesViewModel
    private lateinit var binding: ActivityTramiteBinding


    private val tag="TRAMITE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTramiteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tramitesViewModel = ViewModelProvider(this, TramitesViewModelFactory()).get(TramitesViewModel::class.java)
    }
}