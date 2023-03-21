package com.gamertop.valorantinfo.api.service

import com.google.gson.annotations.SerializedName

class MapListItemResponse(
    @SerializedName("uuid") val uuid: String,
    @SerializedName("displayName") val name: String,
    @SerializedName("displayIcon") val icon: String
    )
class MapListDataResponse(@SerializedName("data") val list: List<MapListItemResponse>)