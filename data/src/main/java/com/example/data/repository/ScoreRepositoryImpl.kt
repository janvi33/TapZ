package com.example.data.repository

import com.example.data.local.dao.ScoreDao
import com.example.data.mapper.toDomain
import com.example.data.mapper.toEntity
import com.example.domain.model.Score
import com.example.domain.repository.ScoreRepository
import kotlinx.coroutines.flow.first

//class ScoreRepositoryImpl(
//    private val dao: ScoreDao
//) : ScoreRepository {
//    override suspend fun getScore(): List<Score> {
//        return dao.getAllScores().first().map { it.toDomain() }
//    }
//
//    override suspend fun saveScore(score: Score) {
//        dao.insertScore(score.toEntity())
//    }
//}
