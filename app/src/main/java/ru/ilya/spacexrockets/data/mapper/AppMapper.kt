package ru.ilya.spacexrockets.data.mapper

import ru.ilya.spacexrockets.data.remote.dto.launches_dto.LaunchDto
import ru.ilya.spacexrockets.data.remote.dto.rockets_dto.*
import ru.ilya.spacexrockets.domain.model.launches_model.Launch
import ru.ilya.spacexrockets.domain.model.rockets_model.Rocket
import javax.inject.Inject

class AppMapper @Inject constructor() {

    fun mapListLaunchDtoToListLaunch(launchesDto: List<LaunchDto>): List<Launch> {
        return launchesDto.map { mapLaunchDtoToLaunch(it) }
    }

    private fun mapLaunchDtoToLaunch(launchDto: LaunchDto): Launch {
        return Launch(
            missionName = launchDto.mission_name ?: EMPTY_STRING,
            launchSuccess = launchDto.launch_success ?: FALSE,
            launchDateUnix = launchDto.launch_date_unix ?: ZERO_INT
        )
    }

    fun mapListRocketDtoToListRocket(rocketsDto: List<RocketDto>): List<Rocket> {
        return rocketsDto.map { mapRocketDtoToRocket(it) }
    }

    private fun mapRocketDtoToRocket(rocketDto: RocketDto): Rocket {
        return Rocket(
            id = rocketDto.id ?: ZERO_INT,
            rocketId = rocketDto.rocket_id ?: EMPTY_STRING,
            rocketName = rocketDto.rocket_name ?: EMPTY_STRING,
            imageUrl = rocketDto.images?.get(FIRST_INDEX) ?: EMPTY_STRING,

            heightFeet = rocketDto.height?.feet ?: ZERO_DOUBLE,
            diameterFeet = rocketDto.diameter?.feet ?: ZERO_DOUBLE,
            massLb = rocketDto.mass?.lb ?: ZERO_INT,
            LeoPayload = rocketDto.payload_weights?.get(FIRST_INDEX)?.lb ?: ZERO_INT,

            firstFlight = rocketDto.first_flight ?: EMPTY_STRING,
            country = rocketDto.country ?: EMPTY_STRING,
            costPerLaunch = rocketDto.cost_per_launch ?: ZERO_INT,

            enginesCountFirstStage = rocketDto.first_stage?.engines ?: ZERO_INT,
            fuelAmountTonsFirstStage = rocketDto.first_stage?.fuel_amount_tons ?: ZERO_DOUBLE,
            burnTimeSecFirstStage = rocketDto.first_stage?.burn_time_sec ?: ZERO_INT,

            enginesCountSecondStage = rocketDto.second_stage?.engines ?: ZERO_INT,
            fuelAmountTonsSecondStage = rocketDto.second_stage?.fuel_amount_tons ?: ZERO_DOUBLE,
            burnTimeSecSecondStage = rocketDto.second_stage?.burn_time_sec ?: ZERO_INT,
        )
    }

    companion object {

        const val ZERO_INT = 0
        const val ZERO_DOUBLE = 0.0
        const val EMPTY_STRING = ""
        const val FIRST_INDEX = 0
        const val FALSE = false
    }
}