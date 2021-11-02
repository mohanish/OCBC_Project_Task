package com.simple.app.ocbc_project_task

import android.app.Application
import com.simple.app.ocbc_project_task.dependencies.DashboardModule
import com.simple.app.ocbc_project_task.dependencies.LoginModule
import com.simple.app.ocbc_project_task.dependencies.NetworkModule
import com.simple.app.ocbc_project_task.dependencies.TransferModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.logger.Level

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        GlobalContext.startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MainApplication)
            modules(listOf(NetworkModule, LoginModule, DashboardModule, TransferModule))
        }
    }
}