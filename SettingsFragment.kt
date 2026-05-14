package com.example.nimmaguru.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import com.example.nimmaguru.NimmaGuruApplication
import com.example.nimmaguru.R
import com.example.nimmaguru.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val indianLanguages = listOf(
        Language("English", "en"),
        Language("Hindi (हिन्दी)", "hi"),
        Language("Bengali (বাংলা)", "bn"),
        Language("Marathi (मराठी)", "mr"),
        Language("Telugu (తెలుగు)", "te"),
        Language("Tamil (தமிழ்)", "ta"),
        Language("Gujarati (ગુજરાતી)", "gu"),
        Language("Urdu (اردو)", "ur"),
        Language("Kannada (ಕನ್ನಡ)", "kn"),
        Language("Odia (ଓଡ଼ିଆ)", "or"),
        Language("Malayalam (മലയാളം)", "ml"),
        Language("Punjabi (ਪੰਜਾਬੀ)", "pa"),
        Language("Assamese (অসমীয়া)", "as"),
        Language("Maithili (मैथिली)", "mai"),
        Language("Santali (संताली)", "sat"),
        Language("Kashmiri (کأشُر)", "ks"),
        Language("Nepali (नेपाली)", "ne"),
        Language("Konkani (कोंकणी)", "kok"),
        Language("Sindhi (سنڌي)", "sd"),
        Language("Dogri (डोगरी)", "doi"),
        Language("Manipuri (মণিপুরী)", "mni"),
        Language("Sanskrit (संस्कृतम्)", "sa"),
        Language("Bodo (बड़ो)", "brx")
    )

    data class Language(val name: String, val code: String) {
        override fun toString(): String = name
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSettingsBinding.bind(view)

        val sessionManager = (requireActivity().application as NimmaGuruApplication).sessionManager
        
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, indianLanguages)
        binding.autoCompleteLanguage.setAdapter(adapter)

        val currentLangCode = sessionManager.getLanguage()
        val currentLang = indianLanguages.find { it.code == currentLangCode } ?: indianLanguages[0]
        binding.autoCompleteLanguage.setText(currentLang.name, false)

        binding.autoCompleteLanguage.setOnItemClickListener { _, _, position, _ ->
            val selectedLang = indianLanguages[position]
            sessionManager.setLanguage(selectedLang.code)
            
            val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(selectedLang.code)
            AppCompatDelegate.setApplicationLocales(appLocale)
        }

        binding.btnChangePassword.setOnClickListener {
            // Implementation for change password
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
