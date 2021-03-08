package com.example.leauge.service

import com.example.leauge.model.Teams
import com.example.leauge.model.TeamsFixture
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FixtureResponse (@SerializedName("teams")
                       @Expose
                       var teams:List<TeamsFixture>){
}