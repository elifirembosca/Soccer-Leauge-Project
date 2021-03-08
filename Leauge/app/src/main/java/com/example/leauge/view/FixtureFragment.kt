package com.example.leauge.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.leauge.R
import com.example.leauge.adapter.FixtureAdapter
import com.example.leauge.adapter.TeamsAdapter
import com.example.leauge.model.Teams
import com.example.leauge.viewmodel.FixtureViewModel
import com.example.leauge.viewmodel.TeamsViewModel
import kotlinx.android.synthetic.main.fragment_fixture.*
import kotlinx.android.synthetic.main.fragment_teams.*

class FixtureFragment : Fragment() {

    private lateinit var viewModel: FixtureViewModel
    private val fixtureAdapter = FixtureAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fixture, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(FixtureViewModel::class.java)
        viewModel.getDataFromAPI()

        recyclerViewFixture.layoutManager =  LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewFixture.adapter = fixtureAdapter
        observeLiveData()

    }

    private fun observeLiveData() {
        viewModel.teamsFixture.observe(viewLifecycleOwner, Observer {teams ->

            teams?.let {
                recyclerViewFixture.visibility = View.VISIBLE
                fixtureAdapter.updateTeamsList(teams)
            }

        })
    }





}