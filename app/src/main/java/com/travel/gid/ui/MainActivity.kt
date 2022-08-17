package com.travel.gid.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.travel.gid.R
import com.travel.gid.data.di.IoDispatcher
import com.travel.gid.utils.ConnectionLiveData
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.directions.DirectionsFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main), ActivityProviding {

    @Inject
    lateinit var networkConnection: ConnectionLiveData

    @Inject
    @IoDispatcher
    lateinit var dispatcherIo: CoroutineDispatcher

    private var tvSnackBarText: TextView? = null
    var snackBar: Snackbar? = null


    private val navController by lazy { findNavController(R.id.container) }
    private val insets by lazy {
        WindowInsetsControllerCompat(window, window.decorView)
    }

    override fun provideNavController() = navController
    override fun provideInsetsController() = insets

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(this)
        DirectionsFactory.initialize(this)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        networkConnection.observe(this) {
            if (!it) {
                snackBar = Snackbar.make(
                    findViewById(R.id.container),
                    "Интернет соединение отсутствует",
                    Snackbar.LENGTH_INDEFINITE
                )
                snackBar!!.let { snak ->
                    tvSnackBarText =
                        snak.view.findViewById(com.google.android.material.R.id.snackbar_text)
                    snak.setAction("Оффлайн") {
                        snak.dismiss()
                        snackBar = null
                        tvSnackBarText = null
                    }
                    snak.show()
                }
            } else {
                snackBar?.setAction("", null)
                CoroutineScope(dispatcherIo).launch {
                    delay(2000)
                    stopSnackBar()
                }
                tvSnackBarText?.text = "Интернет соединение восстановлено"

            }
        }


        this.supportActionBar!!.hide()
    }

    private fun stopSnackBar() {
        snackBar?.dismiss()
    }

    override fun onStop() {
        MapKitFactory.getInstance().onStop();
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart();
    }
}


