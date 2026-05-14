package com.example.nimmaguru.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.nimmaguru.NimmaGuruApplication
import com.example.nimmaguru.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment(R.layout.fragment_splash) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sessionManager = (requireActivity().application as NimmaGuruApplication).sessionManager
        
        lifecycleScope.launch {
            delay(2000)
            if (sessionManager.isLoggedIn()) {
                findNavController().navigate(R.id.action_splashFragment_to_dashboardFragment)
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }
        }
    }
}
