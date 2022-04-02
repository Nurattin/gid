package com.travel.gid.ui

import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController

interface ActivityProviding {
    fun provideNavController(): NavController
    fun provideInsetsController(): WindowInsetsControllerCompat
}