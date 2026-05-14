package com.example.nimmaguru

import android.app.Application
import com.example.nimmaguru.data.local.AppDatabase
import com.example.nimmaguru.data.repository.NimmaGuruRepository
import com.example.nimmaguru.utils.SessionManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import com.example.nimmaguru.data.model.User
import com.example.nimmaguru.data.model.Session
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope

class NimmaGuruApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy {
        NimmaGuruRepository(
            database.userDao(),
            database.guruDao(),
            database.studentDao(),
            database.sessionDao(),
            database.enrollmentDao(),
            database.appreciationDao()
        )
    }
    val sessionManager by lazy { SessionManager(this) }

    override fun onCreate() {
        super.onCreate()
        
        // Pre-populate some data for a professional look on first run
        @Suppress("OPT_IN_USAGE")
        GlobalScope.launch(Dispatchers.IO) {
            val user = repository.getUserByEmail("test@guru.com")
            if (user == null) {
                val guruId = repository.insertUser(User(
                    fullName = "Master Guru",
                    email = "test@guru.com",
                    phone = "9988776655",
                    role = "GURU",
                    village = "Hampi",
                    address = "Main Street, Hampi",
                    languagePreference = "English",
                    profileImageUri = null,
                    passwordHash = "password"
                ))

                repository.insertSession(Session(
                    guruId = guruId,
                    title = "Modern Agriculture Techniques",
                    subject = "Agriculture",
                    date = "2024-06-15",
                    startTime = "09:00 AM",
                    endTime = "11:00 AM",
                    location = "Village Community Center",
                    maxStudents = 20
                ))
                repository.insertSession(Session(
                    guruId = guruId,
                    title = "Basic Digital Literacy",
                    subject = "Technology",
                    date = "2024-06-20",
                    startTime = "02:00 PM",
                    endTime = "04:00 PM",
                    location = "Library",
                    maxStudents = 15
                ))
            }
        }
    }
}
