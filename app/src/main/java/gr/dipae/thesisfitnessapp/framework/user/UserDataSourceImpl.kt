package gr.dipae.thesisfitnessapp.framework.user

import android.content.Intent
import android.content.IntentSender
import android.content.SharedPreferences
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.internal.api.FirebaseNoSignedInUserException
import gr.dipae.thesisfitnessapp.data.history.model.RemoteDaySummary
import gr.dipae.thesisfitnessapp.data.history.model.RemoteSportDone
import gr.dipae.thesisfitnessapp.data.history.model.RemoteWorkoutDone
import gr.dipae.thesisfitnessapp.data.history.model.RemoteWorkoutExerciseDone
import gr.dipae.thesisfitnessapp.data.user.UserDataSource
import gr.dipae.thesisfitnessapp.data.user.diet.model.RemoteUserScannedFood
import gr.dipae.thesisfitnessapp.data.user.login.broadcast.LoginBroadcast
import gr.dipae.thesisfitnessapp.data.user.model.RemoteUser
import gr.dipae.thesisfitnessapp.data.user.workout.model.RemoteUserWorkout
import gr.dipae.thesisfitnessapp.data.user.workout.model.RemoteUserWorkoutExercise
import gr.dipae.thesisfitnessapp.di.module.qualifier.GeneralSharedPrefs
import gr.dipae.thesisfitnessapp.domain.wizard.entity.UserWizardDetails
import gr.dipae.thesisfitnessapp.framework.datastore.CustomPreferencesDataStore
import gr.dipae.thesisfitnessapp.util.DAY_SUMMARY_COLLECTION
import gr.dipae.thesisfitnessapp.util.DAY_SUMMARY_DATE
import gr.dipae.thesisfitnessapp.util.DAY_SUMMARY_SPORTS_DONE_COLLECTION
import gr.dipae.thesisfitnessapp.util.DAY_SUMMARY_WORKOUTS_DONE_COLLECTION
import gr.dipae.thesisfitnessapp.util.DAY_SUMMARY_WORKOUT_EXERCISES_DONE_COLLECTION
import gr.dipae.thesisfitnessapp.util.EXERCISES_COLLECTION
import gr.dipae.thesisfitnessapp.util.GOOGLE_SIGN_IN_BLOCKED_TIME
import gr.dipae.thesisfitnessapp.util.SCANNED_FOODS_COLLECTION
import gr.dipae.thesisfitnessapp.util.USERS_COLLECTION
import gr.dipae.thesisfitnessapp.util.USER_DECLINED_SIGN_IN_COUNTER
import gr.dipae.thesisfitnessapp.util.USER_EMAIL
import gr.dipae.thesisfitnessapp.util.USER_NAME
import gr.dipae.thesisfitnessapp.util.WIZARD_USER_DETAILS
import gr.dipae.thesisfitnessapp.util.WORKOUTS_COLLECTION
import gr.dipae.thesisfitnessapp.util.base.GoogleAuthenticationException
import gr.dipae.thesisfitnessapp.util.ext.get
import gr.dipae.thesisfitnessapp.util.ext.getDocumentResponse
import gr.dipae.thesisfitnessapp.util.ext.getDocumentsResponse
import gr.dipae.thesisfitnessapp.util.ext.getMatchingDocument
import gr.dipae.thesisfitnessapp.util.ext.set
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val googleSignInRequest: BeginSignInRequest,
    private val oneTapClient: SignInClient,
    private val auth: FirebaseAuth,
//    private val db: AppDatabase,
    private val dataStore: CustomPreferencesDataStore,
    @GeneralSharedPrefs private val sharedPrefs: SharedPreferences,
    private val fireStore: FirebaseFirestore,
    private val loginBroadcast: LoginBroadcast
) : UserDataSource {
    override suspend fun initializeGoogleSignIn(): IntentSender {
        return oneTapClient.beginSignIn(googleSignInRequest).await().pendingIntent.intentSender
    }

    private fun getGoogleUserId(googleSignInData: Intent): String {
        val credential = oneTapClient.getSignInCredentialFromIntent(googleSignInData)
        return credential.googleIdToken ?: throw GoogleAuthenticationException()
    }

    override suspend fun isUserSignedIn(): Boolean = auth.currentUser != null

    private fun getFirebaseUserId() = auth.currentUser?.uid ?: throw FirebaseNoSignedInUserException("")

    override suspend fun registerUser() {
        val firebaseUserId = getFirebaseUserId()

        fireStore.collection(USERS_COLLECTION).document(firebaseUserId)
            .set(
                hashMapOf(
                    USER_EMAIL to auth.currentUser?.email,
                    USER_NAME to auth.currentUser?.displayName
                )
            ).await()
    }

    override suspend fun signInUser(googleSignInData: Intent) {
        val firebaseCredential = GoogleAuthProvider.getCredential(getGoogleUserId(googleSignInData), null)
        auth.signInWithCredential(firebaseCredential).await()
    }

    override suspend fun getUser(): RemoteUser? {
        val firebaseUserId = getFirebaseUserId()
        return fireStore.collection(USERS_COLLECTION).document(firebaseUserId).getDocumentResponse<RemoteUser>()
    }

    override suspend fun getUserDetailsLocally(): RemoteUser? {
        //TODO IMPLEMENT WITH REMOTEUSER ENTITY FOR DATADASE
        return null
    }

    override suspend fun logoutUser() {
        auth.signOut()
    }

    override suspend fun logout() {
        auth.signOut()
    }

    override fun getUserDeclinedSignInCount(): Int {
        return sharedPrefs[USER_DECLINED_SIGN_IN_COUNTER, 0] ?: 0
    }

    override fun setGoogleSignInDenialCount(count: Int) {
        sharedPrefs[USER_DECLINED_SIGN_IN_COUNTER] = count
    }

    override suspend fun resetGoogleSignInDenialCount() {
        sharedPrefs[USER_DECLINED_SIGN_IN_COUNTER] = 0
        loginBroadcast.refreshGoogleSignInEnabledState(true)
    }

    override suspend fun setUserWizardDetails(wizardDetails: UserWizardDetails) {
        dataStore.set(WIZARD_USER_DETAILS, wizardDetails)
    }

    override suspend fun getUserWizardDetails(): UserWizardDetails? {
        return dataStore.get(WIZARD_USER_DETAILS)
    }

    override suspend fun setUserFitnessProfile(wizardDetails: UserWizardDetails) {
        fireStore.collection(USERS_COLLECTION).document(getFirebaseUserId()).set(wizardDetails)
    }

    override fun setGoogleSignInBlockedTime() {
        sharedPrefs[GOOGLE_SIGN_IN_BLOCKED_TIME] = Calendar.getInstance().timeInMillis
    }

    override fun getGoogleSignInBlockedTime(): Long {
        return sharedPrefs[GOOGLE_SIGN_IN_BLOCKED_TIME, 0] ?: 0
    }

    override suspend fun getUserWorkouts(): List<RemoteUserWorkout> {
        val userId = getFirebaseUserId()
        return fireStore.collection(USERS_COLLECTION).document(userId).collection(WORKOUTS_COLLECTION).getDocumentsResponse()
    }

    override suspend fun getUserWorkoutExercises(workoutId: String): List<RemoteUserWorkoutExercise> {
        val userId = getFirebaseUserId()
        return fireStore
            .collection(USERS_COLLECTION).document(userId)
            .collection(WORKOUTS_COLLECTION).document(workoutId)
            .collection(EXERCISES_COLLECTION).getDocumentsResponse()
    }

    override suspend fun getDaySummary(): RemoteDaySummary? {
        val userId = getFirebaseUserId()
        val startOfDayTime = getStartTimestampOfThisDay()
        val endOfDayTime = getEndTimeStampOfThisDay()

        return fireStore
            .collection(USERS_COLLECTION)
            .document(userId)
            .collection(DAY_SUMMARY_COLLECTION)
            .whereGreaterThanOrEqualTo(DAY_SUMMARY_DATE, startOfDayTime)
            .whereLessThanOrEqualTo(DAY_SUMMARY_DATE, endOfDayTime)
            .getMatchingDocument<RemoteDaySummary>()
    }

    override suspend fun getDaySummarySportsDone(daySummaryId: String): List<RemoteSportDone> {
        return fireStore
            .collection(USERS_COLLECTION)
            .document(getFirebaseUserId())
            .collection(DAY_SUMMARY_COLLECTION)
            .document(daySummaryId)
            .collection(DAY_SUMMARY_SPORTS_DONE_COLLECTION)
            .getDocumentsResponse()
    }

    override suspend fun getDaySummaryWorkoutsDone(daySummaryId: String): List<RemoteWorkoutDone> {
        return fireStore
            .collection(USERS_COLLECTION)
            .document(getFirebaseUserId())
            .collection(DAY_SUMMARY_COLLECTION)
            .document(daySummaryId)
            .collection(DAY_SUMMARY_WORKOUTS_DONE_COLLECTION)
            .getDocumentsResponse()
    }

    override suspend fun getDaySummaryWorkoutExercisesDone(daySummaryId: String, workoutId: String): List<RemoteWorkoutExerciseDone> {
        return fireStore
            .collection(USERS_COLLECTION)
            .document(getFirebaseUserId())
            .collection(DAY_SUMMARY_COLLECTION)
            .document(daySummaryId)
            .collection(DAY_SUMMARY_WORKOUTS_DONE_COLLECTION)
            .document(workoutId)
            .collection(DAY_SUMMARY_WORKOUT_EXERCISES_DONE_COLLECTION)
            .getDocumentsResponse()
    }

    override suspend fun getFavoriteSportIds(): List<String> {
        return fireStore
            .collection(USERS_COLLECTION)
            .document(getFirebaseUserId())
            .getDocumentResponse<RemoteUser>()?.favoriteActivities?.map { it.removePrefix("/activities/") } ?: emptyList()
    }

    override suspend fun getUserScannedFoods(): List<RemoteUserScannedFood> {
        val userId = getFirebaseUserId()
        return fireStore
            .collection(USERS_COLLECTION)
            .document(userId)
            .collection(SCANNED_FOODS_COLLECTION)
            .getDocumentsResponse()
    }

    private fun getStartTimestampOfThisDay(): Long {
        return Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }.timeInMillis
    }

    private fun getEndTimeStampOfThisDay(): Long {
        return Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 24)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
        }.timeInMillis
    }
}