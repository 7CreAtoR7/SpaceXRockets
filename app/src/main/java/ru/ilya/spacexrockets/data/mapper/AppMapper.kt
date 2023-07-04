package ru.ilya.spacexrockets.data.mapper

import ru.ilya.spacexrockets.data.local.entity.LaunchEntity
import ru.ilya.spacexrockets.data.local.entity.RocketEntity
import ru.ilya.spacexrockets.data.remote.dto.launches_dto.LaunchDto
import ru.ilya.spacexrockets.data.remote.dto.rockets_dto.RocketDto
import ru.ilya.spacexrockets.domain.model.launches_model.Launch
import ru.ilya.spacexrockets.domain.model.rockets_model.Rocket
import javax.inject.Inject

class AppMapper @Inject constructor() {

    fun mapListLaunchDtoToListLaunchEntity(launchesDto: List<LaunchDto>): List<LaunchEntity> {
        return launchesDto.map { mapLaunchDtoToLaunchEntity(it) }
    }

    private fun mapLaunchDtoToLaunchEntity(launchDto: LaunchDto): LaunchEntity {
        return LaunchEntity(
            rocketName = launchDto.rocket?.rocket_name ?: EMPTY_STRING,
            missionName = launchDto.mission_name ?: EMPTY_STRING,
            launchSuccess = launchDto.launch_success ?: FALSE,
            launchDateUnix = launchDto.launch_date_unix ?: ZERO_INT,
        )
    }

    fun mapListLaunchEntityToListLaunch(launchesEntity: List<LaunchEntity>): List<Launch> {
        return launchesEntity.map { mapLaunchEntityToLaunch(it) }
    }

    private fun mapLaunchEntityToLaunch(launchEntity: LaunchEntity): Launch {
        return Launch(
            missionName = launchEntity.missionName,
            launchSuccess = launchEntity.launchSuccess,
            launchDateUnix = launchEntity.launchDateUnix,
            rocketName = launchEntity.rocketName
        )
    }

    fun mapListRocketDtoToListRocketEntity(rocketsDto: List<RocketDto>): List<RocketEntity> {
        return rocketsDto.map { mapRocketDtoToRocketEntity(it) }
    }

    private fun mapRocketDtoToRocketEntity(rocketDto: RocketDto): RocketEntity {
        return RocketEntity(
            rocketId = rocketDto.rocket_id ?: EMPTY_STRING,
            rocketName = rocketDto.rocket_name ?: EMPTY_STRING,
            imageUrl = rocketDto.images?.get(FIRST_INDEX) ?: EMPTY_STRING,
            heightM = rocketDto.height?.meters ?: ZERO_DOUBLE,
            heightFeet = rocketDto.height?.feet ?: ZERO_DOUBLE,
            diameterM = rocketDto.diameter?.meters ?: ZERO_DOUBLE,
            diameterFeet = rocketDto.diameter?.feet ?: ZERO_DOUBLE,
            massKg = rocketDto.mass?.kg ?: ZERO_INT,
            massLb = rocketDto.mass?.lb ?: ZERO_INT,
            LeoPayloadKg = rocketDto.payload_weights?.get(FIRST_INDEX)?.kg ?: ZERO_INT,
            LeoPayloadLb = rocketDto.payload_weights?.get(FIRST_INDEX)?.lb ?: ZERO_INT,
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

    fun mapListRocketEntityToListRocket(rocketsEntity: List<RocketEntity>): List<Rocket> {
        return rocketsEntity.map { mapRocketEntityToRocket(it) }
    }

    private fun mapRocketEntityToRocket(rocketEntity: RocketEntity): Rocket {
        return Rocket(
            id = rocketEntity.id,
            rocketId = rocketEntity.rocketId,
            rocketName = rocketEntity.rocketName,
            imageUrl = rocketEntity.imageUrl,
            heightM = rocketEntity.heightM,
            heightFeet = rocketEntity.heightFeet,
            diameterM = rocketEntity.diameterM,
            diameterFeet = rocketEntity.diameterFeet,
            massKg = rocketEntity.massKg,
            massLb = rocketEntity.massLb,
            LeoPayloadKg = rocketEntity.LeoPayloadKg,
            LeoPayloadLb = rocketEntity.LeoPayloadLb,
            firstFlight = rocketEntity.firstFlight,
            country = rocketEntity.country,
            costPerLaunch = rocketEntity.costPerLaunch,
            enginesCountFirstStage = rocketEntity.enginesCountFirstStage,
            fuelAmountTonsFirstStage = rocketEntity.fuelAmountTonsFirstStage,
            burnTimeSecFirstStage = rocketEntity.burnTimeSecFirstStage,
            enginesCountSecondStage = rocketEntity.enginesCountSecondStage,
            fuelAmountTonsSecondStage = rocketEntity.fuelAmountTonsSecondStage,
            burnTimeSecSecondStage = rocketEntity.burnTimeSecSecondStage,
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