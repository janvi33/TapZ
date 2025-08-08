package com.example.domain.repository

import com.example.domain.model.Card

interface CardRepository {
    suspend fun getAllCards(): List<Card>
}