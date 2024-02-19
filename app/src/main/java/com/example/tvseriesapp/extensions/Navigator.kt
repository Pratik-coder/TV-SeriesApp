package com.example.tvseriesapp.extensions

import android.app.Activity
import android.content.Intent
import com.example.tvseriesapp.ui.HomeActivity


fun Activity.navigateToHome(){
    this.startActivity(Intent(this,HomeActivity::class.java))
    this.finish()
}
