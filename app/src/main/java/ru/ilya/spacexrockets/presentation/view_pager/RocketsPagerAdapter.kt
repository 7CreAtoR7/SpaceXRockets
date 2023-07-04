package ru.ilya.spacexrockets.presentation.view_pager

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.ilya.spacexrockets.domain.model.rockets_model.Rocket
import ru.ilya.spacexrockets.presentation.rockets_screen.RocketsFragment

class RocketsPagerAdapter(
    fragmentManager: FragmentManager,
    private val rocketsList: List<Rocket>,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private var currentPosition = 0

    override fun getItemCount(): Int {
        return rocketsList.size
    }

    override fun createFragment(position: Int): Fragment {
        val currentRocketInfo = rocketsList[position]
        currentPosition = position
        return RocketsFragment.newInstance(currentRocketInfo)
    }

}