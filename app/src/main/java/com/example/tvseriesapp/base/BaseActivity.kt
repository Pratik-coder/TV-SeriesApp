package com.example.tvseriesapp.base

import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.tvseriesapp.utils.WindowUtils
import com.example.tvseriesapp.utils.WindowUtils.clearStatusBarActivity
import com.example.tvseriesapp.utils.WindowUtils.setToolBarTopPadding
import com.example.tvseriesapp.utils.WindowUtils.setUpDarkTheme
import com.example.tvseriesapp.utils.WindowUtils.setUpLightTheme
import com.example.tvseriesapp.utils.WindowUtils.setUpTransparentStatusBar

open class BaseActivity:AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpTransparentStatusBar(this)
        applyTheme()
    }


    fun clearStatusBar(){
       clearStatusBarActivity(this)
    }

    override fun setSupportActionBar(toolbar: Toolbar?) {
        super.setSupportActionBar(toolbar)
        toolbar?.let {
           setToolBarTopPadding(toolbar)
        }
    }

    private fun applyTheme(){
        when(resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK){
            Configuration.UI_MODE_NIGHT_YES->{
                setUpDarkTheme(this)
            }
            else->{
                setUpLightTheme(this)
            }
        }
    }

}