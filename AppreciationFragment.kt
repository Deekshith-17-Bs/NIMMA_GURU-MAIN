package com.example.nimmaguru.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.nimmaguru.NimmaGuruApplication
import com.example.nimmaguru.R
import com.example.nimmaguru.data.model.Appreciation
import com.example.nimmaguru.databinding.FragmentAppreciationBinding
import com.example.nimmaguru.ui.viewmodel.MainViewModel
import com.example.nimmaguru.ui.viewmodel.ViewModelFactory

class AppreciationFragment : Fragment(R.layout.fragment_appreciation) {
    private var _binding: FragmentAppreciationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory((requireActivity().application as NimmaGuruApplication).repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAppreciationBinding.bind(view)

        val sessionManager = (requireActivity().application as NimmaGuruApplication).sessionManager
        val studentId = sessionManager.getUserId()

        binding.btnPostAppreciation.setOnClickListener {
            val message = binding.etMessage.text.toString()
            if (message.isNotEmpty()) {
                // For demo purposes, we'll post this for Guru ID 1
                val appreciation = Appreciation(studentId = studentId, guruId = 1, message = message)
                viewModel.postAppreciation(appreciation)
                Toast.makeText(requireContext(), "Thank you note posted!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
