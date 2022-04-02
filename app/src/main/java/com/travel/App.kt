package com.travel

import androidx.multidex.MultiDexApplication
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.directions.DirectionsFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : MultiDexApplication() {
    companion object {
        var screenWidth = 0
    }

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("ba4c094d-26ad-47b3-84a0-e3f7674c208b")
        MapKitFactory.initialize(this)
        DirectionsFactory.initialize(this)
    }
}