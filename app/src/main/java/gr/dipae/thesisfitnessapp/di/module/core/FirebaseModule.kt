package gr.dipae.thesisfitnessapp.di.module.core

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object FirebaseModule {
    //Firebase
    @Provides
    fun provideFirebaseFireStore(): FirebaseFirestore =
        FirebaseFirestore.getInstance()

    @Provides
    fun provideFirebaseAuthentication(): FirebaseAuth =
        FirebaseAuth.getInstance()
}