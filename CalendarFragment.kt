package com.example.nimmaguru.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nimmaguru.NimmaGuruApplication
import com.example.nimmaguru.R
import com.example.nimmaguru.databinding.FragmentCalendarBinding
import com.example.nimmaguru.ui.adapter.SessionAdapter
import com.example.nimmaguru.ui.viewmodel.MainViewModel
import com.example.nimmaguru.ui.viewmodel.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CalendarFragment : Fragment(R.layout.fragment_calendar) {
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory((requireActivity().application as NimmaGuruApplication).repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCalendarBinding.bind(view)

        val adapter = SessionAdapter()
        binding.rvSessions.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSessions.adapter = adapter

        lifecycleScope.launch {
            viewModel.allSessions.collectLatest { sessions ->
                adapter.submitList(sessions)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
