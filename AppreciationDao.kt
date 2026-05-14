package com.example.nimmaguru.data.local

import androidx.room.*
import com.example.nimmaguru.data.model.Appreciation
import kotlinx.coroutines.flow.Flow

@Dao
interface AppreciationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppreciation(appreciation: Appreciation)

    @Query("SELECT * FROM appreciations WHERE guruId = :guruId ORDER BY date DESC")
    fun getAppreciationsForGuru(guruId: Long): Flow<List<Appreciation>>

    @Query("SELECT * FROM appreciations ORDER BY date DESC")
    fun getAllAppreciations(): Flow<List<Appreciation>>
}
