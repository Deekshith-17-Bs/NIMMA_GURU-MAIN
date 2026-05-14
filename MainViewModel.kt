package com.example.nimmaguru.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nimmaguru.data.model.GuruWithProfile
import com.example.nimmaguru.data.model.Appreciation
import com.example.nimmaguru.data.model.GuruProfile
import com.example.nimmaguru.data.model.Session
import com.example.nimmaguru.data.repository.NimmaGuruRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(private val repository: NimmaGuruRepository) : ViewModel() {

    val allGurus = repository.getAllGurus().stateIn(
        viewModelScope, SharingStarted.Lazily, emptyList()
    )

    val allSessions = repository.getAllSessions().stateIn(
        viewModelScope, SharingStarted.Lazily, emptyList()
    )

    fun getUserById(id: Long) = repository.getUserById(id)

    fun createSession(session: Session) {
        viewModelScope.launch {
            repository.insertSession(session)
        }
    }

    fun enrollInSession(sessionId: Long, studentId: Long) {
        viewModelScope.launch {
            repository.enroll(com.example.nimmaguru.data.model.Enrollment(sessionId = sessionId, studentId = studentId, status = "Joined"))
        }
    }

    fun getGuruProfile(userId: Long) = repository.getGuruProfile(userId)

    fun updateGuruProfile(userId: Long, name: String, phone: String, skills: String, availability: String, village: String) {
        viewModelScope.launch {
            // Update User info
            val user = repository.getUserById(userId).firstOrNull()
            user?.let {
                repository.updateUser(it.copy(fullName = name, phone = phone, village = village))
            }
            
            // Update or Insert Guru Profile
            val profile = repository.getGuruProfile(userId).firstOrNull()
            if (profile != null) {
                repository.insertGuruProfile(profile.copy(skills = skills, availableTimeSlots = availability))
            } else {
                repository.insertGuruProfile(GuruProfile(userId = userId, skills = skills, availableTimeSlots = availability, experienceYears = 0, bio = "", availableDays = "", locationDetails = ""))
            }
        }
    }


    fun postAppreciation(appreciation: Appreciation) {
        viewModelScope.launch {
            repository.insertAppreciation(appreciation)
        }
    }

    fun getAppreciationsForGuru(guruId: Long) = repository.getAppreciationsForGuru(guruId)
}
