package com.gamertop.valorantinfo.api.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/v1/agents")
    suspend fun getCharacterList(): Response<CharacterListDataResponse>

    @GET("/v1/agents/{uuid}")
    suspend fun getCharacter(@Path("uuid") characterUuid: String): Response<CharacterDataResponse>

    @GET("/v1/agents")
    suspend fun getAgentList(): Response<AgentListDataResponse>

    @GET("/v1/agents/{uuid}")
    suspend fun getAgent(@Path("uuid") agentUuid: String): Response<AgentDataResponse>

    @GET("/v1/maps")
    suspend fun getMapList(): Response<MapListDataResponse>

    @GET("/v1/maps/{uuid}")
    suspend fun getMap(@Path("uuid") mapUuid: String): Response<MapDataResponse>
}