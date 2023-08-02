package gr.dipae.thesisfitnessapp.di.module.core

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.data.sport.SportsDataSource
import gr.dipae.thesisfitnessapp.data.sport.SportsRepositoryImpl
import gr.dipae.thesisfitnessapp.data.sport.broadcast.SportSessionBreakTimerBroadcast
import gr.dipae.thesisfitnessapp.data.sport.broadcast.SportSessionBroadcast
import gr.dipae.thesisfitnessapp.data.sport.broadcast.StopWatchBroadcast
import gr.dipae.thesisfitnessapp.domain.sport.SportsRepository
import gr.dipae.thesisfitnessapp.framework.service.StopWatchService
import gr.dipae.thesisfitnessapp.framework.sport.SportsDataSourceImpl
import gr.dipae.thesisfitnessapp.framework.sport.location.SportLocationProvider
import kotlinx.coroutines.DelicateCoroutinesApi

@Module
@InstallIn(ViewModelComponent::class)
object SportModule {
    @Provides
    @ViewModelScoped
    fun provideSportSessionBreakTimerBroadcast(): SportSessionBreakTimerBroadcast {
        return SportSessionBreakTimerBroadcast.getInstance()
    }

    @Provides
    @ViewModelScoped
    fun provideSportSessionBroadcast(): SportSessionBroadcast {
        return SportSessionBroadcast.getInstance()
    }

    @Provides
    @ViewModelScoped
    fun provideSportLocationProvider(@ApplicationContext context: Context): SportLocationProvider {
        return SportLocationProvider(context)
    }

}

@Module
@InstallIn(ViewModelComponent::class)
interface SportBindsModule {
    @Binds
    fun bindSportRepository(repository: SportsRepositoryImpl): SportsRepository

    @Binds
    fun bindSportDataSource(dataSource: SportsDataSourceImpl): SportsDataSource
}

@DelicateCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object SportSessionStopWatchModule {

    @Provides
    fun provideStopWatchBroadcast(): StopWatchBroadcast {
        return StopWatchBroadcast.getInstance()
    }

    @Provides
    fun provideNotificationBuilder(@ApplicationContext context: Context): NotificationCompat.Builder {
        val openAppIntent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        openAppIntent?.flags = Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            openAppIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(context, StopWatchService.STOP_WATCH_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.app_logo)
            .setContentTitle(context.getString(R.string.sport_session_stop_watch_notification_title))
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .setContentIntent(pendingIntent)
    }
}