package ru.ilya.spacexrockets.presentation.rockets_screen

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ru.ilya.spacexrockets.databinding.FragmentRocketsBinding
import ru.ilya.spacexrockets.domain.model.rockets_model.Rocket
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ru.ilya.spacexrockets.domain.model.rockets_model.RocketFeaturesModel
import ru.ilya.spacexrockets.presentation.rockets_screen.features_adapter.RocketsFeaturesAdapter
import ru.ilya.spacexrockets.presentation.view_pager.RocketsViewPagerFragmentDirections

class RocketsFragment : Fragment() {

    private var _binding: FragmentRocketsBinding? = null
    private val binding: FragmentRocketsBinding
        get() = _binding ?: throw RuntimeException("FragmentRocketsBinding == null")

    private lateinit var currentRocket: Rocket

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("CHECKMYFRAGMENTS", "RocketsFragment onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            currentRocket = it.getParcelable(CURRENT_ROCKET) ?: throw IllegalStateException(NO_ROCKET_EXCEPTION)
        }
        Log.d("CHECKMYFRAGMENTS", "RocketsFragment onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).supportActionBar?.hide()
        _binding = FragmentRocketsBinding.inflate(inflater, container, false)
        Log.d("CHECKMYFRAGMENTS", "RocketsFragment onCreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind()
        initClickListeners()
        Log.d("CHECKMYFRAGMENTS", "RocketsFragment onViewCreated")
    }

    private fun bind() {
        loadImage()
        binding.rocketTitle.text = currentRocket.rocketName

        binding.firstLaunchDate.text = currentRocket.firstFlight
        binding.country.text = currentRocket.country
        binding.launchPrice.text = currentRocket.costPerLaunch.toString()

        binding.enginesCountFirstStage.text = currentRocket.enginesCountFirstStage.toString()
        binding.fuelCountFirstStage.text = currentRocket.fuelAmountTonsFirstStage.toString()
        binding.burnTimeFirstStage.text = currentRocket.burnTimeSecFirstStage.toString()

        binding.enginesCountSecondStage.text = currentRocket.enginesCountSecondStage.toString()
        binding.fuelCountSecondStage.text = currentRocket.fuelAmountTonsSecondStage.toString()
        binding.burnTimeSecondStage.text = currentRocket.burnTimeSecSecondStage.toString()

        val featuresList = listOf(
            RocketFeaturesModel("Высота", currentRocket.heightFeet),
            RocketFeaturesModel("Диаметр", currentRocket.diameterFeet),
            RocketFeaturesModel("Масса", currentRocket.massLb.toDouble()),
            RocketFeaturesModel("Нагрузка", currentRocket.LeoPayload.toDouble()),
        )

        val adapter = RocketsFeaturesAdapter(featuresList)
        binding.rocketFeatureRv.adapter = adapter
    }

    private fun initClickListeners() {
        binding.checkLaunches.setOnClickListener {
            findNavController().navigate(RocketsViewPagerFragmentDirections.actionRocketsViewPagerFragmentToLaunchesFragment(currentRocket.rocketName))
        }

        binding.settingsButton.setOnClickListener {
            findNavController().navigate(RocketsViewPagerFragmentDirections.actionRocketsViewPagerFragmentToSettingsFragment())
        }
    }

    private fun loadImage() {
        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)

        Glide.with(this)
            .load(currentRocket.imageUrl)
            .apply(requestOptions)
            .into(binding.rocketImage)
    }

    companion object {

        private const val CURRENT_ROCKET = "CURRENT_ROCKET"
        private const val NO_ROCKET_EXCEPTION = "No Rocket Found"

        @JvmStatic
        fun newInstance(currentRocket: Rocket) =
            RocketsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(CURRENT_ROCKET, currentRocket)
                }
            }
    }

    override fun onPause() {
        super.onPause()
        Log.d("CHECKMYFRAGMENTS", "RocketsFragment onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("CHECKMYFRAGMENTS", "RocketsFragment onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("CHECKMYFRAGMENTS", "RocketsFragment onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("CHECKMYFRAGMENTS", "RocketsFragment onDetach")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}