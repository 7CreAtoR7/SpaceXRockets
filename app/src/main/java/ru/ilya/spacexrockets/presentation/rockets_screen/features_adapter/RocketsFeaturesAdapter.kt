package ru.ilya.spacexrockets.presentation.rockets_screen.features_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.ilya.spacexrockets.databinding.RocketFeatureItemBinding
import ru.ilya.spacexrockets.domain.model.RocketFeaturesModel

class RocketsFeaturesAdapter(
    private val featuresModel: List<RocketFeaturesModel>
) : RecyclerView.Adapter<RocketsFeaturesAdapter.RocketsViewHolder>() {

    class RocketsViewHolder(
        val binding: RocketFeatureItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RocketFeatureItemBinding.inflate(inflater, parent, false)
        return RocketsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RocketsViewHolder, position: Int) {
        val rocketItem = featuresModel[position]

        holder.binding.value.text = rocketItem.value.toString()
        holder.binding.textValue.text = rocketItem.feature
    }

    override fun getItemCount(): Int {
        return featuresModel.size
    }
}