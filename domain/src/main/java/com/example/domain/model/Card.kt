package com.example.domain.model


//For API
//data class Card (
//    val suit: String?,
//    val value: String,
//    val image: String?
//)


//for temporary use
data class Card(
    val id: Int,
    val emoji: String,
    val isMatched: Boolean = false,
    val isFaceUp: Boolean = false
)
