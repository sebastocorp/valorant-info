package com.gamertop.valorantinfo.api.service

import com.google.gson.annotations.SerializedName

class AgentListItemResponse(
    @SerializedName("uuid") val uuid: String,
    @SerializedName("displayName") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("displayIcon") val icon: String,
    @SerializedName("isPlayableCharacter") val isPlayable: Boolean
    )
class AgentListDataResponse(@SerializedName("data") val list: List<AgentListItemResponse>)