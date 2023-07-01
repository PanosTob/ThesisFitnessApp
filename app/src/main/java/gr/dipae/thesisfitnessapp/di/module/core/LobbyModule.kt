package gr.dipae.thesisfitnessapp.di.module.core

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.components.SingletonComponent
import gr.dipae.thesisfitnessapp.data.lobby.LobbyDataSource
import gr.dipae.thesisfitnessapp.data.lobby.LobbyRepositoryImpl
import gr.dipae.thesisfitnessapp.domain.lobby.LobbyRepository
import gr.dipae.thesisfitnessapp.framework.lobby.LobbyDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
object LobbyModule

@Module
@InstallIn(ActivityRetainedComponent::class)
interface LobbyBindsModule {
    @Binds
    fun bindLobbyRepository(repository: LobbyRepositoryImpl): LobbyRepository

    @Binds
    fun bindLobbyDataSource(dataSource: LobbyDataSourceImpl): LobbyDataSource
}