package com.gamertop.valorantinfo.api.service

import com.google.gson.annotations.SerializedName

class CharacterAbilitiesResponse(
    @SerializedName("displayName") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("displayIcon") val icon: String
)
class CharacterResponse(
    @SerializedName("displayName") val name: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("displayIcon") val img: String = "",
    @SerializedName("abilities") val abilities: List<CharacterAbilitiesResponse> = emptyList()
)
class CharacterDataResponse(@SerializedName("data") val data: CharacterResponse)