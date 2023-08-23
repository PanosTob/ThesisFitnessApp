package gr.dipae.thesisfitnessapp.domain.user.entity

import gr.dipae.thesisfitnessapp.domain.diet.entity.Food
import gr.dipae.thesisfitnessapp.domain.history.entity.DaySummary
import gr.dipae.thesisfitnessapp.domain.user.challenges.entity.UserSportChallenge
import gr.dipae.thesisfitnessapp.domain.workout.entity.Workout

data class User(
    val name: String,
    val email: String,
    val imgUrl: String,
    val bodyWeight: Double,
    val muscleMassPercent: Double,
    val bodyFatPercent: Double,
    val dailyStepsGoal: Long,
    val dailyCaloricBurnGoal: Long,
    val fitnessLevel: FitnessLevel,
    val dietGoal: DietGoal,
    val favoriteActivities: List<String>,
    val daySummaries: List<DaySummary>,
    val scannedFoods: List<Food>,
    val workouts: List<Workout>,
    val sportChallenges: List<UserSportChallenge>
)

data class DietGoal(
    val calories: Long,
    val carbohydrates: Long,
    val fats: Long,
    val protein: Long,
    val waterML: Long
)

enum class FitnessLevel {
    Beginner, Intermediate, Advanced, Unknown
}