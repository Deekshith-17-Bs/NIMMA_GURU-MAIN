package com.example.nimmaguru.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nimmaguru.data.model.User
import com.example.nimmaguru.data.repository.NimmaGuruRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: NimmaGuruRepository) : ViewModel() {
    private val _userState = MutableStateFlow<User?>(null)
    val userState: StateFlow<User?> = _userState

    private val _loginStatus = MutableStateFlow<Boolean?>(null)
    val loginStatus: StateFlow<Boolean?> = _loginStatus

    fun register(user: User) {
        viewModelScope.launch {
            repository.insertUser(user)
        }
    }

    fun login(email: String, passwordHash: String) {
        viewModelScope.launch {
            // Auto-create diverse mentor accounts for a better experience
            if (email == "test@guru.com" && repository.getUserByEmail(email) == null) {
                val mentors = listOf(
                    User(fullName = "Master Guru", email = email, phone = "9988776655", role = "GURU", village = "Hampi", address = "Main St", languagePreference = "English", profileImageUri = null, passwordHash = "password"),
                    User(fullName = "Dr. Ramesh (Science)", email = "ramesh@guru.com", phone = "9876543210", role = "GURU", village = "Ramapura", address = "Village Center", languagePreference = "Kannada", profileImageUri = null, passwordHash = "password"),
                    User(fullName = "Shanti Devi (Tailoring)", email = "shanti@guru.com", phone = "9123456789", role = "GURU", village = "Hampi", address = "Market Road", languagePreference = "Hindi", profileImageUri = null, passwordHash = "password"),
                    User(fullName = "Basavaraj (Carpentry)", email = "basu@guru.com", phone = "9345678901", role = "GURU", village = "Koppal", address = "Wood Lane", languagePreference = "Kannada", profileImageUri = null, passwordHash = "password"),
                    User(fullName = "Sita Patil (Math)", email = "sita@guru.com", phone = "9567890123", role = "GURU", village = "Badami", address = "School St", languagePreference = "English", profileImageUri = null, passwordHash = "password"),
                    User(fullName = "Gurappa (Organic Farming)", email = "guru@farm.com", phone = "9789012345", role = "GURU", village = "Hampi", address = "Green Farm", languagePreference = "Kannada", profileImageUri = null, passwordHash = "password"),
                    User(fullName = "Anjali (Yoga)", email = "anjali@guru.com", phone = "9901234567", role = "GURU", village = "Mysuru", address = "Peace St", languagePreference = "English", profileImageUri = null, passwordHash = "password")
                )
                mentors.forEach { repository.insertUser(it) }
            }

            val user = repository.getUserByEmail(email)
            if (user != null && user.passwordHash == passwordHash) {
                _userState.value = user
                _loginStatus.value = true
            } else {
                _loginStatus.value = false
            }
        }
    }

    fun resetLoginStatus() {
        _loginStatus.value = null
    }
}
