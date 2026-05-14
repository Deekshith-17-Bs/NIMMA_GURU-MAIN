package com.example.nimmaguru.data.local

import androidx.room.*
import com.example.nimmaguru.data.model.GuruProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface GuruDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: GuruProfile)

    @Query("SELECT * FROM guru_profiles WHERE userId = :userId")
    fun getProfileByUserId(userId: Long): Flow<GuruProfile?>

    @Query("SELECT * FROM guru_profiles WHERE skills LIKE '%' || :skill || '%'")
    fun searchGurusBySkill(skill: String): Flow<List<GuruProfile>>
}
