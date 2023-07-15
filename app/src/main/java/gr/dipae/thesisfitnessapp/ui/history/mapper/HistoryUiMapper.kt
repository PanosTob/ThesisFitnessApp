package gr.dipae.thesisfitnessapp.ui.history.mapper

import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.domain.history.entity.DaySummary
import gr.dipae.thesisfitnessapp.ui.history.model.DaySummaryUiItem
import gr.dipae.thesisfitnessapp.ui.history.model.HistoryUiState
import gr.dipae.thesisfitnessapp.ui.history.model.toDailyDietUiItem
import gr.dipae.thesisfitnessapp.ui.sport.model.toSportUiItem
import gr.dipae.thesisfitnessapp.ui.workout.model.toWorkoutUiItem
import javax.inject.Inject

class HistoryUiMapper @Inject constructor() : Mapper {

    operator fun invoke(daySummary: DaySummary): HistoryUiState {
        return HistoryUiState(
            daySummary = mapDaySummaryUiItem(daySummary)
        )
    }

    private fun mapDaySummaryUiItem(daySummary: DaySummary?): DaySummaryUiItem? {
        return daySummary?.let {
            DaySummaryUiItem(
                steps = it.steps.toString(),
                date = it.date.toString(),
                dailyDiet = it.dailyDiet.toDailyDietUiItem(),
                sportActionsDone = it.sportActionsDone.map { it.toSportUiItem() },
                workoutsDone = it.workoutsDone.map { it.toWorkoutUiItem() }
            )
        }
    }
}