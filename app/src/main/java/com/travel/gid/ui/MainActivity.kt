package com.travel.gid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.findNavController
import com.travel.gid.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main), ActivityProviding {

    private val navController by lazy { findNavController(R.id.container) }
    private val insets by lazy {
        WindowInsetsControllerCompat(window, window.decorView)
    }

    override fun provideNavController() = navController
    override fun provideInsetsController() = insets

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        this.supportActionBar!!.hide()
    }
}


