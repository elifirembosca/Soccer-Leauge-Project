package com.example.leauge.service

class ApiUtils {

    companion object{

        val BASE_URL = "https://raw.githubusercontent.com/"

        fun getTeamsInterface(): TeamsAPI {
            return RetrofitClient.getClient(BASE_URL).create(TeamsAPI::class.java)
        }


    }

}