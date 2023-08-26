package com.example.spacenewsfeed

import android.app.Application
import com.example.spacenewsfeed.models.AppContainer
import com.example.spacenewsfeed.models.DefaultAppContainer

class NewsApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}