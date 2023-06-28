package gr.dipae.thesisfitnessapp.di.module.core

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import gr.dipae.thesisfitnessapp.domain.session.SessionHandler

@Module
@InstallIn(ActivityComponent::class)
class SessionModule {

    @Provides
    fun provideSessionHandler(): SessionHandler {
        return SessionHandler.getInstance()
    }
}