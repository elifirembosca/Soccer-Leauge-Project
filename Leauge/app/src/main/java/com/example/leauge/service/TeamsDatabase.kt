package com.example.leauge.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.leauge.model.Teams

@Database(entities = [Teams::class],version = 1)
abstract class TeamsDatabase: RoomDatabase() {

    abstract fun teamsDao() : TeamsDao


    companion object{

        @Volatile private var instance : TeamsDatabase?=null

        private val lock=Any()

        operator fun invoke(context: Context) = instance?: synchronized(lock){
            instance?: makeDatabase(context).also {
                instance = it
            }
        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,TeamsDatabase::class.java,"teams"
        ).build()

    }

}