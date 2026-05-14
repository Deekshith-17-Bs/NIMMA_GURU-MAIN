package com.example.nimmaguru.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.nimmaguru.NimmaGuruApplication
import com.example.nimmaguru.R
import com.example.nimmaguru.data.model.Session
import com.example.nimmaguru.databinding.FragmentCreateSessionBinding
import com.example.nimmaguru.ui.viewmodel.MainViewModel
import com.example.nimmaguru.ui.viewmodel.ViewModelFactory

class CreateSessionFragment : Fragment(R.layout.fragment_create_session) {
    private var _binding: FragmentCreateSessionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory((requireActivity().application as NimmaGuruApplication).repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCreateSessionBinding.bind(view)

        val guruId = (requireActivity().application as NimmaGuruApplication).sessionManager.getUserId()

        binding.btnCreateSession.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val subject = binding.etSubject.text.toString()
            val date = binding.etDate.text.toString()
            val startTime = binding.etStartTime.text.toString()
            val endTime = binding.etEndTime.text.toString()
            val location = binding.etLocation.text.toString()
            val maxStudents = binding.etMaxStudents.text.toString().toIntOrNull() ?: 10

            if (title.isNotEmpty() && subject.isNotEmpty() && date.isNotEmpty()) {
                val session = Session(
                    guruId = guruId,
                    title = title,
                    subject = subject,
                    date = date,
                    startTime = startTime,
                    endTime = endTime,
                    location = location,
                    maxStudents = maxStudents
                )
                viewModel.createSession(session)
                Toast.makeText(requireContext(), "Session Created", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "Fill required fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
