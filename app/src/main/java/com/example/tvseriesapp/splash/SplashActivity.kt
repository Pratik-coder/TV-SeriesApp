package com.example.tvseriesapp.splash

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tvseriesapp.R
import com.example.tvseriesapp.base.BaseActivity
import com.example.tvseriesapp.extensions.navigateToHome


class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateToHome()
    }
}