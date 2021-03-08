package com.example.leauge.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.leauge.R
import com.example.leauge.model.Teams
import com.example.leauge.util.downloadFromUrl
import com.example.leauge.util.placeholderProgressBar
import kotlinx.android.synthetic.main.card_teams.view.*

class TeamsAdapter(val teamsList: ArrayList<Teams>):
    RecyclerView.Adapter<TeamsAdapter.TeamViewHolder>()  {
    
    class TeamViewHolder(var view: View) : RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.card_teams, parent, false)
        return TeamViewHolder(view)

    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {

        holder.view.textViewName.text=teamsList[position].teamName
        holder.view.textViewStadium.text=teamsList[position].teamStadium
        holder.view.imageViewTeam.downloadFromUrl("${teamsList[position].teamImage}", placeholderProgressBar(holder.view.context))

    }

    override fun getItemCount(): Int {
        return teamsList.size
    }

    fun updateTeamsList(newTeamList: List<Teams>){
        teamsList.clear()
        teamsList.addAll(newTeamList)
        notifyDataSetChanged()
    }


}