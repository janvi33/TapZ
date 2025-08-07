package com.example.data.local.dao

import androidx.room.*
import com.example.data.local.entity.ScoreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScore(score: ScoreEntity)

    @Query("SELECT * FROM score")
    fun getAllScores(): Flow<List<ScoreEntity>>
}
