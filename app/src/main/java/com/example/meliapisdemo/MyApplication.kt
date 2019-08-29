package com.example.meliapisdemo

import android.app.Application
import com.example.meliapisdemo.utils.Prefs
import com.facebook.drawee.backends.pipeline.Fresco

class MyApplication : Application() {

    companion object{
        lateinit var prefs: Prefs
    }
        override fun onCreate() {
            super.onCreate()
            Fresco.initialize(this);
            prefs = Prefs(applicationContext)
        }
}