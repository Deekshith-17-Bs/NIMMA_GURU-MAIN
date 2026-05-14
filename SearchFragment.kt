package com.example.nimmaguru.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.ChipGroup
import com.example.nimmaguru.NimmaGuruApplication
import com.example.nimmaguru.R
import com.example.nimmaguru.data.model.GuruWithProfile
import com.example.nimmaguru.databinding.FragmentSearchBinding
import com.example.nimmaguru.ui.adapter.GuruAdapter
import com.example.nimmaguru.ui.viewmodel.MainViewModel
import com.example.nimmaguru.ui.viewmodel.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchFragment : Fragment(R.layout.fragment_search) {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory((requireActivity().application as NimmaGuruApplication).repository)
    }

    private lateinit var guruAdapter: GuruAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)

        setupRecyclerView()
        setupFilters()
        setupSearch()
    }

    private var allGurusList = emptyList<GuruWithProfile>()

    private fun setupRecyclerView() {
        guruAdapter = GuruAdapter { guru ->
            // Handle Guru click
        }
        binding.rvGurus.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = guruAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allGurus.collectLatest { gurus ->
                allGurusList = gurus
                applyFilters()
            }
        }
    }

    private fun setupFilters() {
        binding.cgSkills.setOnCheckedStateChangeListener { _: ChipGroup, _: List<Int> ->
            applyFilters()
        }
    }

    private fun setupSearch() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                applyFilters()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        
        binding.tilSearch.setEndIconOnClickListener {
            // Voice Search triggered
            Toast.makeText(requireContext(), "Voice search starting...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun applyFilters() {
        val query = binding.etSearch.text.toString().lowercase()
        
        val selectedChipId = binding.cgSkills.checkedChipId
        val selectedSkill = when (selectedChipId) {
            R.id.chipMath -> "Math"
            R.id.chipScience -> "Science"
            R.id.chipCarpentry -> "Carpentry"
            else -> ""
        }
        
        val filteredList = allGurusList.filter { item ->
            val matchesQuery = item.user.fullName.lowercase().contains(query) || 
                             item.user.village.lowercase().contains(query)
            
            val matchesSkill = if (selectedSkill.isEmpty()) true 
                              else item.profile?.skills?.contains(selectedSkill, ignoreCase = true) == true
            
            matchesQuery && matchesSkill
        }
        guruAdapter.submitList(filteredList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
