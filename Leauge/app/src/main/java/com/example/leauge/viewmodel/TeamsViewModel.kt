package com.example.leauge.viewmodel

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.leauge.model.Teams
import com.example.leauge.service.ApiUtils
import com.example.leauge.service.TeamsAPI
import com.example.leauge.service.TeamsDatabase
import com.example.leauge.service.TeamsResponse
import com.example.leauge.util.CustomSharedPreferences
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback


class TeamsViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var teamsApiService : TeamsAPI
    val teams = MutableLiveData<List<Teams>>()
    val teamsError = MutableLiveData<Boolean>()
    val teamsLoading = MutableLiveData<Boolean>()


    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L
    private var custompref= CustomSharedPreferences(getApplication())



    fun RefreshData(){

        val updateTime = custompref.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            getDataFromSQlite()
        } else {
            getDataFromAPI()
        }

    }



    private fun getDataFromSQlite(){
        teamsLoading.value=true
        teamsError.value=false
        launch {
            val teams =TeamsDatabase(getApplication()).teamsDao().getAllTeams()
            showTeams(teams)
           // Toast.makeText(getApplication(),"Teams From SQLite",Toast.LENGTH_SHORT).show()
        }
    }



    fun getDataFromAPI() {
        teamsLoading.value=true
        teamsError.value=false

        teamsApiService = ApiUtils.getTeamsInterface()

        teamsApiService.getTeams().enqueue(object : Callback<TeamsResponse> {
            override fun onResponse(call: Call<TeamsResponse>?, response: retrofit2.Response<TeamsResponse>?) {
                if(response != null){
                    val liste = response.body()!!.teams
                    storeInSqlite(liste)
                   // Toast.makeText(getApplication(),"Menu From API",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<TeamsResponse>?, t: Throwable?) {

            }
        })
    }

    private fun showTeams(liste: List<Teams>){
        teams.value=liste
        teamsError.value=false
        teamsLoading.value=false
    }


    fun refreshDataFromAPI(){
        getDataFromAPI()
    }


    private fun storeInSqlite(liste : List<Teams>){
        launch {
            val dao = TeamsDatabase(getApplication()).teamsDao()
            dao.deleteAllTeams()
            val listLong = dao.insertAll(*liste.toTypedArray())
            var i =0
            while (i<liste.size){
                liste[i].uuid = listLong[i].toInt()
                i=i+1
            }

            showTeams(liste)
        }

        custompref.saveTime(System.nanoTime())
    }



}