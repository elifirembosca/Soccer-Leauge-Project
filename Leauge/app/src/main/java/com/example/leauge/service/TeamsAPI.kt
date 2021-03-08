package com.example.leauge.service

import com.example.leauge.model.Teams
import retrofit2.Call
import retrofit2.http.GET

interface TeamsAPI {

    @GET("/elifirembosca/LeaugeApi/main/mock_api.json")
    fun getTeams(): Call<TeamsResponse>

    @GET("/elifirembosca/LeaugeApi/main/mock_api.json")
    fun getTeamsFixture(): Call<FixtureResponse>



}