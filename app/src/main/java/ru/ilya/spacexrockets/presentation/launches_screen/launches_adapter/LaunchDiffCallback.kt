package ru.ilya.spacexrockets.presentation.launches_screen.launches_adapter

import androidx.recyclerview.widget.DiffUtil
import ru.ilya.spacexrockets.domain.model.launches_model.Launch

class LaunchDiffCallback : DiffUtil.ItemCallback<Launch>() {
    override fun areItemsTheSame(oldItem: Launch, newItem: Launch): Boolean {
        return oldItem.missionName == newItem.missionName
    }

    override fun areContentsTheSame(oldItem: Launch, newItem: Launch): Boolean {
        return oldItem == newItem
    }
}