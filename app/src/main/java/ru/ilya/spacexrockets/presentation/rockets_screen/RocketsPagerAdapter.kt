package ru.ilya.spacexrockets.presentation.rockets_screen

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.ilya.spacexrockets.domain.model.Rocket

class RocketsPagerAdapter(
    fragmentManager: FragmentManager,
    private val rocketsList: List<Rocket>
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int {
        return rocketsList.size
    }

    override fun getItem(position: Int): Fragment {
        val currentRocketInfo = rocketsList[position]
        return RocketsFragment.newInstance(currentRocketInfo)
    }

}