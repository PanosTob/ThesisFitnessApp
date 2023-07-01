package gr.dipae.thesisfitnessapp.di.module.core

import android.content.Context
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import gr.dipae.thesisfitnessapp.data.user.UserDataSource
import gr.dipae.thesisfitnessapp.data.user.UserRepositoryImpl
import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.framework.user.UserDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Provides
    fun provideGoogleOneTapClient(@ApplicationContext context: Context): SignInClient {
        return Identity.getSignInClient(context)
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