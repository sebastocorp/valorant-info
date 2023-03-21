package com.gamertop.valorantinfo.api.service

import com.google.gson.annotations.SerializedName

class AgentAbilitiesResponse(
    @SerializedName("displayName") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("displayIcon") val icon: String
)
class AgentResponse(
    @SerializedName("displayName") val name: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("displayIcon") val img: String = "",
    @SerializedName("abilities") val abilities: List<AgentAbilitiesResponse> = emptyList()
)
class AgentDataResponse(@SerializedName("data") val data: AgentResponse)