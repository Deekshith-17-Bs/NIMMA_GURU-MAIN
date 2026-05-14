package com.example.nimmaguru.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("nimma_guru_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_ROLE = "user_role"
        private const val KEY_LANGUAGE = "language"
    }

    fun saveUser(userId: Long, role: String) {
        prefs.edit().apply {
            putLong(KEY_USER_ID, userId)
            putString(KEY_USER_ROLE, role)
            apply()
        }
    }

    fun getUserId(): Long = prefs.getLong(KEY_USER_ID, -1L)
    fun getUserRole(): String? = prefs.getString(KEY_USER_ROLE, null)

    fun isLoggedIn(): Boolean = getUserId() != -1L

    fun logout() {
        prefs.edit().clear().apply()
    }

    fun setLanguage(language: String) {
        prefs.edit().putString(KEY_LANGUAGE, language).apply()
    }

    fun getLanguage(): String = prefs.getString(KEY_LANGUAGE, "en") ?: "en"
}
