package gr.dipae.thesisfitnessapp.di.module.core

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.components.SingletonComponent
import gr.dipae.thesisfitnessapp.data.user.UserDataSource
import gr.dipae.thesisfitnessapp.data.user.UserRepositoryImpl
import gr.dipae.thesisfitnessapp.data.user.broadcast.AccelerometerBroadcast
import gr.dipae.thesisfitnessapp.data.user.broadcast.StepCounterBroadcast
import gr.dipae.thesisfitnessapp.data.user.login.broadcast.LoginBroadcast
import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.framework.user.UserDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
object UserModule {
    @Provides
    fun provideLoginBroadCast(): LoginBroadcast {
        return LoginBroadcast.getInstance()
    }

    @Provides
    fun provideStepCounterBroadcast(): StepCounterBroadcast {
        return StepCounterBroadcast.getInstance()
    }

    @Provides
    fun provideAccelerometerBroadcast(): AccelerometerBroadcast {
        return AccelerometerBroadcast.getInstance()
    }
}

@Module
@InstallIn(ActivityRetainedComponent::class)
interface UserBindsModule {
    @Binds
    fun bindUserRepository(repository: UserRepositoryImpl): UserRepository

    @Binds
    fun bindUserDataSource(dataSource: UserDataSourceImpl): UserDataSource
}