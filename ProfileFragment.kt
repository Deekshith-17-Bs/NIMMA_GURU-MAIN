package com.example.nimmaguru.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.nimmaguru.NimmaGuruApplication
import com.example.nimmaguru.R
import com.example.nimmaguru.databinding.FragmentProfileBinding
import com.example.nimmaguru.ui.viewmodel.MainViewModel
import com.example.nimmaguru.ui.viewmodel.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory((requireActivity().application as NimmaGuruApplication).repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        val sessionManager = (requireActivity().application as NimmaGuruApplication).sessionManager
        val userId = sessionManager.getUserId()

        lifecycleScope.launch {
            viewModel.getUserById(userId).collectLatest { user ->
                user?.let {
                    binding.etFullName.setText(it.fullName)
                    binding.etPhone.setText(it.phone)
                    binding.etVillage.setText(it.village)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.getGuruProfile(userId).collectLatest { profile ->
                profile?.let {
                    binding.etSkills.setText(it.skills)
                    binding.etAvailability.setText(it.availableTimeSlots)
                }
            }
        }

        binding.btnUpdateProfile.setOnClickListener {
            val name = binding.etFullName.text.toString()
            val phone = binding.etPhone.text.toString()
            val village = binding.etVillage.text.toString()
            val skills = binding.etSkills.text.toString()
            val availability = binding.etAvailability.text.toString()

            if (name.isNotEmpty()) {
                viewModel.updateGuruProfile(userId, name, phone, skills, availability, village)
                Toast.makeText(requireContext(), "Profile Updated", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
