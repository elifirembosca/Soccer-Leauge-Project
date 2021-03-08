package com.example.leauge.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class Teams(
        @ColumnInfo(name="name")
        @SerializedName("name")
        @Expose
        val teamName: String,

        @ColumnInfo(name="imageUrl")
        @SerializedName("imageUrl")
        @Expose
        val teamImage: String,

        @ColumnInfo(name="stadium")
        @SerializedName("stadium")
        @Expose
        val teamStadium: String) {

        @PrimaryKey(autoGenerate = true)
        var uuid:Int=0

}