package com.example.memory.presentation.score

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Score
import com.example.domain.repository.ScoreRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ScoreViewModel @Inject constructor(
    private val scoreRepository: ScoreRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    var scoreList by mutableStateOf<List<Score>>(emptyList())
        private set

    fun loadScores() {
        val email = auth.currentUser?.email ?: return
        viewModelScope.launch {
            scoreList = scoreRepository.getScoreForUser(email)
        }
    }

    fun saveScore(bestScore: Int, bestTime: Int) {
        val email = auth.currentUser?.email ?: return
        val score = Score(playerEmail = email, bestScore = bestScore, bestTime = bestTime)
        viewModelScope.launch {
            scoreRepository.saveScore(score)
            loadScores() // refresh
        }
    }
}


