package gr.dipae.thesisfitnessapp.data.history.mapper

import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.data.history.model.RemoteDaySummary
import gr.dipae.thesisfitnessapp.domain.history.entity.DaySummary
import javax.inject.Inject

class HistoryMapper @Inject constructor() : Mapper {

    operator fun invoke(remoteDaySummary: RemoteDaySummary?): DaySummary? {
        return remoteDaySummary?.toDaySummary()
    }
}