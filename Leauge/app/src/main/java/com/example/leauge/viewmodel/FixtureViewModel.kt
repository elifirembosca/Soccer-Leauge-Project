package com.example.leauge.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.leauge.model.TeamsFixture
import com.example.leauge.service.ApiUtils
import com.example.leauge.service.FixtureResponse
import com.example.leauge.service.TeamsAPI
import com.example.leauge.util.CustomSharedPreferences
import retrofit2.Call
import retrofit2.Callback

class FixtureViewModel(application: Application) : BaseViewModel(application){

    private lateinit var teamsApiService : TeamsAPI
    val teamsFixture = MutableLiveData<List<TeamsFixture>>()


    fun getDataFromAPI() {
        teamsApiService = ApiUtils.getTeamsInterface()
        teamsApiService.getTeamsFixture().enqueue(object : Callback<FixtureResponse> {
            override fun onResponse(call: Call<FixtureResponse>?, response: retrofit2.Response<FixtureResponse>?) {
                if(response != null){
                    val liste = response.body()!!.teams
                    showTeams(liste)
                    //Toast.makeText(getApplication(),"Fixture From API", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<FixtureResponse>?, t: Throwable?) {

            }
        })
    }

    private fun showTeams(liste: List<TeamsFixture>){
        teamsFixture.value=liste
    }


}
