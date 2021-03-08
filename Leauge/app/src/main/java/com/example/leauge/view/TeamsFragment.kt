package com.example.leauge.view

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.leauge.R
import com.example.leauge.adapter.TeamsAdapter
import com.example.leauge.model.Teams
import com.example.leauge.util.CustomSharedPreferences
import com.example.leauge.viewmodel.TeamsViewModel
import kotlinx.android.synthetic.main.fragment_teams.*

class TeamsFragment : Fragment() {

    private lateinit var viewModel: TeamsViewModel
    private val teamsAdapter = TeamsAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teams, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(this).get(TeamsViewModel::class.java)
        viewModel.RefreshData()

        recyclerViewTeams.setHasFixedSize(true)
        recyclerViewTeams.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
        recyclerViewTeams.adapter=teamsAdapter

        swipe.setOnRefreshListener {
            recyclerViewTeams.visibility = View.GONE
            teamsError.visibility = View.GONE
            teamsLoading.visibility = View.VISIBLE
            viewModel.refreshDataFromAPI()
            swipe.isRefreshing = false
        }
        observeLiveData()

    }



    private fun observeLiveData() {
        viewModel.teams.observe(viewLifecycleOwner, Observer {teams ->

            teams?.let {
                recyclerViewTeams.visibility = View.VISIBLE
                teamsAdapter.updateTeamsList(teams)
            }

        })

        viewModel.teamsError.observe(viewLifecycleOwner, Observer { error->
            error?.let {
                if(it) {
                    teamsError.visibility = View.VISIBLE
                } else {
                    teamsError.visibility = View.GONE
                }
            }
        })

        viewModel.teamsLoading.observe(viewLifecycleOwner, Observer { loading->
            loading?.let {
                if (it) {
                    teamsLoading.visibility = View.VISIBLE
                    recyclerViewTeams.visibility = View.GONE
                    teamsError.visibility = View.GONE
                } else {
                    teamsLoading.visibility = View.GONE
                }
            }
        })
    }

}