package ru.ilya.spacexrockets.presentation.launches_screen.launches_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import ru.ilya.spacexrockets.R
import ru.ilya.spacexrockets.databinding.LaunchItemBinding
import ru.ilya.spacexrockets.domain.model.launches_model.Launch
import ru.ilya.spacexrockets.util.fromUnixToDate

class LaunchesAdapter : ListAdapter<Launch, LaunchesAdapter.LaunchViewHolder>(LaunchDiffCallback()) {

    class LaunchViewHolder(
        val binding: LaunchItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LaunchItemBinding.inflate(inflater, parent, false)
        return LaunchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
        val launchItem = getItem(position)
        with(holder.binding) {
            launchName.text = launchItem.missionName
            launchDate.text = launchItem.launchDateUnix.fromUnixToDate()
            if (launchItem.launchSuccess)
                statusImage.setImageResource(R.drawable.success_launch)
            else
                statusImage.setImageResource(R.drawable.fail_launch)
        }
    }
}