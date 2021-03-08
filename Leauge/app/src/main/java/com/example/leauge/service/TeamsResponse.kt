package com.example.leauge.service

import com.example.leauge.model.Teams
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TeamsResponse(@SerializedName("teams")
                    @Expose
                    var teams:List<Teams>){
}