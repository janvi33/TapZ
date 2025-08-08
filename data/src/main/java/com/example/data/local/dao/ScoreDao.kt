package com.example.data.local.dao

import androidx.room.*
import com.example.data.local.entity.ScoreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDao {

    @Query("SELECT * FROM score")
    fun getAllScores(): Flow<List<ScoreEntity>>

    @Query("SELECT * FROM score WHERE playerEmail = :email")
    fun getScoresByEmail(email: String): Flow<List<ScoreEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScore(score: ScoreEntity)

    @Delete
    suspend fun deleteScore(score: ScoreEntity)

}
