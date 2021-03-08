package com.example.leauge.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.leauge.model.Teams

@Dao
interface TeamsDao {

    @Insert
    suspend fun insertAll(vararg teams: Teams) :List<Long>

    @Query("SELECT * FROM teams")
    suspend fun getAllTeams():List<Teams>


    @Query("DELETE FROM teams")
    suspend fun deleteAllTeams()

}