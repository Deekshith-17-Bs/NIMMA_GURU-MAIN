package com.example.nimmaguru.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.nimmaguru.NimmaGuruApplication
import com.example.nimmaguru.R
import com.example.nimmaguru.data.model.User
import com.example.nimmaguru.databinding.FragmentRegisterBinding
import com.example.nimmaguru.ui.viewmodel.AuthViewModel
import com.example.nimmaguru.ui.viewmodel.ViewModelFactory

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels {
        ViewModelFactory((requireActivity().application as NimmaGuruApplication).repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRegisterBinding.bind(view)

        binding.btnRegister.setOnClickListener {
            val fullName = binding.etFullName.text.toString()
            val email = binding.etEmail.text.toString()
            val phone = binding.etPhone.text.toString()
            val password = binding.etPassword.text.toString()
            val role = if (binding.rbGuru.isChecked) "GURU" else "STUDENT"

            if (fullName.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && password.isNotEmpty()) {
                val user = User(
                    fullName = fullName,
                    email = email,
                    phone = phone,
                    role = role,
                    village = "", // To be filled in profile
                    address = "",
                    languagePreference = "English",
                    profileImageUri = null,
                    passwordHash = password // hashing should be here
                )
                viewModel.register(user)
                Toast.makeText(requireContext(), "Registered Successfully", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "Fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
