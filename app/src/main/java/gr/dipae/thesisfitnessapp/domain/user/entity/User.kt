package gr.dipae.thesisfitnessapp.domain.user.entity

import gr.dipae.thesisfitnessapp.domain.diet.entity.Food
import gr.dipae.thesisfitnessapp.domain.history.entity.DaySummary
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
    val workouts: List<Workout>
)

data class DietGoal(
    val calories: Long,
    val carbohydrates: Double,
    val fats: Double,
    val protein: Double,
    val waterML: Double
)

enum class FitnessLevel {
    Beginner, Intermediate, Advanced, Unknown
}