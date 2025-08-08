// data/remote/dto/CharacterResponse.kt
package com.example.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("items") val items: List<CharacterDto>
)
