package com.example.fullscreen

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val windowInsetsController by lazy { WindowCompat.getInsetsController(window, window.decorView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // make "fitsSystemWindows" parameter work in xml:
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.activity_main)

        // use notch/cutout in fullscreen:
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode = LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }

        window.statusBarColor = Color.TRANSPARENT
        // transparent nav bar works with "gesture mode", but doesn't work with "buttons mode"
        // on Samsung for some reason. So use almost transparent:
        window.navigationBarColor = Color.parseColor("#01ffffff")

        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        windowInsetsController.isAppearanceLightStatusBars = true
        windowInsetsController.isAppearanceLightNavigationBars = true
        buttonBarsAreLight.isActivated = true

        buttonIsFullScreen.setOnClickListener {
            it.isActivated = !it.isActivated
            if (it.isActivated) {
                buttonIsFullScreen.text = "disable full screen"
                windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
            } else {
                buttonIsFullScreen.text = "enable full screen"
                windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
            }
        }

        buttonBarsAreLight.setOnClickListener {
            it.isActivated = !it.isActivated
            windowInsetsController.isAppearanceLightStatusBars = it.isActivated
            windowInsetsController.isAppearanceLightNavigationBars = it.isActivated
            if (it.isActivated) {
                buttonBarsAreLight.text = "make light bars"
            } else {
                buttonBarsAreLight.text = "make dark bars"
            }
        }
    }
}