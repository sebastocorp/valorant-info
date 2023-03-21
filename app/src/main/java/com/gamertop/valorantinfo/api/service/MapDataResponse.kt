package com.gamertop.valorantinfo.api.service

import com.google.gson.annotations.SerializedName

class MapResponse(
    @SerializedName("displayName") val name: String = "",
    @SerializedName("splash") val img: String = ""
)
class MapDataResponse(@SerializedName("data") val data: MapResponse)