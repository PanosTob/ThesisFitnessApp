package gr.dipae.thesisfitnessapp.di.module.core

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import gr.dipae.thesisfitnessapp.ui.livedata.NetworkLiveData
import gr.dipae.thesisfitnessapp.util.connectivity.network.ConnectivityMonitor
import gr.dipae.thesisfitnessapp.util.connectivity.network.NougatConnectivityMonitor

@Module
@InstallIn(SingletonComponent::class)
object ConnectivityModule {

    @Provides
    fun provideConnectivityManager(application: Application): ConnectivityManager {
        return application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    fun provideConnectivityLiveData(monitor: ConnectivityMonitor): NetworkLiveData {
        return NetworkLiveData(monitor)
    }

    @Provides
    fun provideConnectivityMonitor(
        @ApplicationContext context: Context,
        connectivityManager: ConnectivityManager
    ): ConnectivityMonitor {
        return NougatConnectivityMonitor(connectivityManager)
    }
}