package com.example.leauge.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class TeamsFixture(
        @SerializedName("name")
        @Expose
        val teamName: String){

}