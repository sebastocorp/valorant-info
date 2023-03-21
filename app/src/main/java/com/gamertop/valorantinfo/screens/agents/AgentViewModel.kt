package com.gamertop.valorantinfo.screens.agents

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gamertop.valorantinfo.api.service.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AgentViewModel: ViewModel() {
    private val retrofit: Retrofit = getARetrofit()

    private val _agent = MutableLiveData<AgentResponse>()
    var agent: LiveData<AgentResponse> = _agent

    private val _isAgentLoading = MutableLiveData<Boolean>()
    val isAgentLoading: LiveData<Boolean> = _isAgentLoading

    suspend fun getAgent(uuid: String) {
        _isAgentLoading.value = true
        val response: Response<AgentDataResponse> = this.retrofit.create(ApiService::class.java).getAgent(uuid)
        if (response.isSuccessful) {
            val responseBody: AgentDataResponse? = response.body()
            if (responseBody != null) {
                _agent.value = responseBody.data
            }
        }
        _isAgentLoading.value = false
    }

    private fun getARetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://valorant-api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}