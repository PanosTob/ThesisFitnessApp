package gr.dipae.thesisfitnessapp.di.module.core

import android.content.Context
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import gr.dipae.thesisfitnessapp.BuildConfig
import gr.dipae.thesisfitnessapp.R

@Module
@InstallIn(SingletonComponent::class)
object GoogleModule {
    //Google Sign In
    @Provides
    fun provideGoogleOneTapClient(@ApplicationContext context: Context): SignInClient {
        return Identity.getSignInClient(context)
    }

    @Provides
    fun provideBeginGoogleSignInRequest(@ApplicationContext context: Context): BeginSignInRequest {
        return BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Google Console Auth 2.0 Web client key.
                    .setServerClientId(context.getString(R.string.default_web_client_id))
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()
    }

    //Firebase
    @Provides
    fun provideFirebaseFireStore(): FirebaseFirestore =
        FirebaseFirestore.getInstance()

    @Provides
    fun provideFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance(BuildConfig.GOOGLE_STORAGE_FIREBASE)
    }

    @Provides
    fun provideFirebaseAuthentication(): FirebaseAuth =
        FirebaseAuth.getInstance()
}