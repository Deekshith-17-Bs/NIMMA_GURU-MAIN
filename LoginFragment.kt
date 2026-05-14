package com.example.nimmaguru.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.nimmaguru.NimmaGuruApplication
import com.example.nimmaguru.R
import com.example.nimmaguru.databinding.FragmentLoginBinding
import com.example.nimmaguru.ui.viewmodel.AuthViewModel
import com.example.nimmaguru.ui.viewmodel.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest

class LoginFragment : Fragment(R.layout.fragment_login) {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels {
        ViewModelFactory((requireActivity().application as NimmaGuruApplication).repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(email, password) // Password hashing should be here
            } else {
                Toast.makeText(requireContext(), "Fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.loginStatus.collectLatest { success ->
                if (success == true) {
                    val user = viewModel.userState.value
                    if (user != null) {
                        (requireActivity().application as NimmaGuruApplication).sessionManager.saveUser(user.id, user.role)
                        viewModel.resetLoginStatus()
                        if (findNavController().currentDestination?.id == R.id.loginFragment) {
                            findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                        }
                    }
                } else if (success == false) {
                    Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_SHORT).show()
                    viewModel.resetLoginStatus()
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
