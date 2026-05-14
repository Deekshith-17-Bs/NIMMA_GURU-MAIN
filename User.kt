package com.example.nimmaguru.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val fullName: String,
    val email: String,
    val phone: String,
    val role: String, // "GURU" or "STUDENT"
    val village: String,
    val address: String,
    val languagePreference: String, // "Kannada" or "English"
    val profileImageUri: String?,
    val passwordHash: String,
    val createdAt: Long = System.currentTimeMillis()
)
