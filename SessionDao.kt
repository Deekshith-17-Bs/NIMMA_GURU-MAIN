package com.example.nimmaguru.data.local

import androidx.room.*
import com.example.nimmaguru.data.model.Session
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: Session)

    @Query("SELECT * FROM sessions")
    fun getAllSessions(): Flow<List<Session>>

    @Query("SELECT * FROM sessions WHERE guruId = :guruId")
    fun getSessionsByGuru(guruId: Long): Flow<List<Session>>

    @Query("SELECT * FROM sessions WHERE id = :sessionId")
    suspend fun getSessionById(sessionId: Long): Session?
}
