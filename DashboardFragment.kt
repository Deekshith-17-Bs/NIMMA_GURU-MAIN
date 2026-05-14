package com.example.nimmaguru.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.nimmaguru.NimmaGuruApplication
import com.example.nimmaguru.R
import com.example.nimmaguru.databinding.FragmentDashboardBinding
import com.example.nimmaguru.ui.viewmodel.MainViewModel
import com.example.nimmaguru.ui.viewmodel.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory((requireActivity().application as NimmaGuruApplication).repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDashboardBinding.bind(view)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            private var lastPressedTime: Long = 0
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() - lastPressedTime < 2000) {
                    requireActivity().finish()
                } else {
                    lastPressedTime = System.currentTimeMillis()
                    Toast.makeText(requireContext(), "Press back again to exit", Toast.LENGTH_SHORT).show()
                }
            }
        })

        val sessionManager = (requireActivity().application as NimmaGuruApplication).sessionManager
        val userId = sessionManager.getUserId()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getUserById(userId).collectLatest { user ->
                    user?.let {
                        binding.tvWelcome.text = "Namaste, ${it.fullName}"
                    }
                }
            }
        }

        binding.btnSearchGuru.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_searchFragment)
        }

        binding.btnViewCalendar.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_calendarFragment)
        }

        binding.btnProfile.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_profileFragment)
        }

        binding.btnSettings.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_settingsFragment)
        }

        binding.cardWallOfFame.setOnClickListener {
            // Navigate to Wall of Fame / Top Gurus
            findNavController().navigate(R.id.action_dashboardFragment_to_appreciationFragment)
        }

        binding.btnLogout.setOnClickListener {
            sessionManager.logout()
            findNavController().navigate(R.id.action_dashboardFragment_to_loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
