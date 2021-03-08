package com.example.leauge.adapter


import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.leauge.R
import com.example.leauge.model.Match
import com.example.leauge.model.MatchWeek
import com.example.leauge.model.TeamsFixture
import kotlinx.android.synthetic.main.card_fixture.view.*

class FixtureAdapter(val teamsList: ArrayList<TeamsFixture>):
        RecyclerView.Adapter<FixtureAdapter.FixtureViewHolder>() {

    var matchFixture = mutableListOf<MatchWeek>()
    private lateinit var context :Context

    class FixtureViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FixtureViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.card_fixture, parent, false)

        matchFixture = ListMatches(teamsList)
        context = parent.getContext()
        return FixtureViewHolder(view)

    }

    override fun onBindViewHolder(holder: FixtureViewHolder, position: Int) {

        holder.view.textViewWeeks.text="${holder.adapterPosition+1}. Week"

        holder.view.fixtureContainer.removeAllViews()

        var matchWeek = matchFixture[position]


        matchWeek.matchs.forEach{
            val textView = TextView(context)
            textView.text="${it.teamHome.teamName} — ${it.teamAway.teamName}"
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.setMargins(5, 10, 5, 10)
            params.gravity=Gravity.CENTER
            textView.layoutParams = params
            textView.gravity=Gravity.CENTER
            textView.textSize=17f
            textView.setTextColor(ContextCompat.getColor(context, R.color.textColor))
            holder.view.fixtureContainer.addView(textView)
        }

    }

    override fun getItemCount(): Int {
       var size:Int
        if(teamsList.size%2==0){
           size= (teamsList.size-1)*2
        }else{
            size=teamsList.size*2
        }
        return size
    }

    fun updateTeamsList(newTeamList: List<TeamsFixture>){
        teamsList.clear()
        teamsList.addAll(newTeamList)
        notifyDataSetChanged()
    }


    fun ListMatches(ListTeam: ArrayList<TeamsFixture>):MutableList<MatchWeek> {

        val matchWeeks = mutableListOf<MatchWeek>()

        if (ListTeam.size % 2 != 0)
        {
            ListTeam.add(TeamsFixture("Bye"))
        }

        val numWeeks = (ListTeam.size - 1)
        val halfSize = ListTeam.size / 2

        val teams = mutableListOf<TeamsFixture>()
        teams.addAll(ListTeam)
        teams.removeAt(0)
        val teamsSize = teams.size

        for (week in 0 until numWeeks * 2)
        {
            val reversed = week >= numWeeks
            val teamIdx = week % teamsSize

            val matchs = mutableListOf<Match>()

            if (ListTeam[0].teamName != "Bye" && teams[teamIdx].teamName != "Bye"){
                val match = if (reversed) Match(ListTeam[0], teams[teamIdx]) else Match(teams[teamIdx], ListTeam[0])
                matchs.add(match)
            }

            for (idx in 1 until halfSize) {
                val firstTeam = (week + idx) % teamsSize
                val secondTeam = (week + teamsSize - idx) % teamsSize

                if (teams[firstTeam].teamName != "Bye" && teams[secondTeam].teamName != "Bye") {
                    val match = if(reversed) Match(teams[secondTeam], teams[firstTeam]) else Match(teams[firstTeam], teams[secondTeam])
                    matchs.add(match)
                }
            }

            val matchWeek = MatchWeek(matchs)
            matchWeeks.add(matchWeek)
        }
        return matchWeeks
    }

            
}