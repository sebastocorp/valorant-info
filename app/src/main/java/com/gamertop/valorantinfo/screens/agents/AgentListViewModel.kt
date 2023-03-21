package com.gamertop.valorantinfo.screens.agents

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gamertop.valorantinfo.api.service.AgentListDataResponse
import com.gamertop.valorantinfo.api.service.AgentListItemResponse
import com.gamertop.valorantinfo.api.service.ApiService
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AgentListViewModel: ViewModel() {
    private val retrofit: Retrofit = getALRetrofit()

    private val _agentList = MutableLiveData<List<AgentListItemResponse>>()
    var agentList: LiveData<List<AgentListItemResponse>> = _agentList

    private val _isAgentListLoading = MutableLiveData<Boolean>()
    val isAgentListLoading: LiveData<Boolean> = _isAgentListLoading

    suspend fun getAgentList() {
        _isAgentListLoading.value = true
        val response: Response<AgentListDataResponse> = this.retrofit.create(ApiService::class.java).getAgentList()
        if (response.isSuccessful) {
            val responseBody: AgentListDataResponse? = response.body()
            if (responseBody != null) {
                _agentList.value = responseBody.list
            }
        }
        _isAgentListLoading.value = false
    }

    private fun getALRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://valorant-api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}