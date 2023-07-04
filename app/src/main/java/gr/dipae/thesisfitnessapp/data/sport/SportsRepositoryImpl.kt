package gr.dipae.thesisfitnessapp.data.sport

import gr.dipae.thesisfitnessapp.data.sport.mapper.SportsMapper
import gr.dipae.thesisfitnessapp.domain.sport.SportsRepository
import gr.dipae.thesisfitnessapp.domain.sport.entity.Sport
import javax.inject.Inject

class SportsRepositoryImpl @Inject constructor(
    private val dataSource: SportsDataSource,
    private val sportsMapper: SportsMapper
) : SportsRepository {
    override suspend fun getSports(): List<Sport> {
        return sportsMapper(dataSource.getSports())
    }
}