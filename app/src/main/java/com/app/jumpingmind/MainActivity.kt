package com.app.jumpingmind

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.proceed_button).setOnClickListener {
            findNavController(R.id.nav_host_container).setGraph(R.navigation.nav_rates)
            it.visibility = View.GONE
        }
    }
}