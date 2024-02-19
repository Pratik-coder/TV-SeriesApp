package com.example.tvseriesapp.ui

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.tvseriesapp.R
import com.example.tvseriesapp.base.BaseActivity
import com.example.tvseriesapp.databinding.ActivityHomeBinding
import com.example.tvseriesapp.extensions.setUpWithNavController

class HomeActivity : BaseActivity() {
    private lateinit var homeinding: ActivityHomeBinding
    private var currentNavController:LiveData<NavController>?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeinding=ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeinding.root)
        setSupportActionBar(homeinding.toolbar)
        if (savedInstanceState==null){
            setUpBottomNavigationView()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setUpBottomNavigationView()
    }

    private fun setUpBottomNavigationView(){
        val controller=homeinding.bottomNavigation.setUpWithNavController(
            listOf(R.navigation.navigation_airingtoday,R.navigation.navigation_ontheair,R.navigation.navigation_search,R.navigation.navigation_favourite),
            supportFragmentManager,
            R.id.nav_host_container,
            intent
        )
        controller.observe(this, Observer { navController->
            setupActionBarWithNavController(navController)
        })
        currentNavController=controller

    }

    override fun onSupportNavigateUp()=currentNavController?.value?.navigateUp() ?:false

}