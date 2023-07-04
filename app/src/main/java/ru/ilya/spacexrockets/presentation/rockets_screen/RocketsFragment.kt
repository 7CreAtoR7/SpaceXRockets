package ru.ilya.spacexrockets.presentation.rockets_screen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import ru.ilya.spacexrockets.R
import ru.ilya.spacexrockets.databinding.FragmentRocketsBinding
import ru.ilya.spacexrockets.domain.model.rockets_model.Rocket
import ru.ilya.spacexrockets.domain.model.rockets_model.RocketFeaturesModel
import ru.ilya.spacexrockets.presentation.rockets_screen.features_adapter.RocketsFeaturesAdapter
import ru.ilya.spacexrockets.presentation.view_pager.RocketsViewPagerFragmentDirections
import ru.ilya.spacexrockets.util.Constants
import ru.ilya.spacexrockets.util.formatToRusDate
import ru.ilya.spacexrockets.util.toEasyReadStringNumber
import ru.ilya.spacexrockets.util.toShortPriceName

class RocketsFragment : Fragment() {

    private var _binding: FragmentRocketsBinding? = null
    private val binding: FragmentRocketsBinding
        get() = _binding ?: FragmentRocketsBinding.inflate(layoutInflater).also { _binding = it }

    private lateinit var currentRocket: Rocket

    // горизонтальный список с характеристиками
    private lateinit var featuresList: MutableList<RocketFeaturesModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            currentRocket =
                it.getParcelable(CURRENT_ROCKET) ?: throw IllegalStateException(NO_ROCKET_EXCEPTION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).supportActionBar?.hide()
        _binding = FragmentRocketsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startFragmentAppearingAnimation()

        getPreviousScrollPosition() // скролимся к ранее сохраненной позиции
        loadSwitchValuesFromSharedPreferences() // список характеристик, учитывая настрйоки
        bind()
        initClickListeners()
    }

    private fun startFragmentAppearingAnimation() {
        val fadeInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        view?.startAnimation(fadeInAnimation)
    }

    private fun getPreviousScrollPosition() {
        val sharedPreferences = requireContext().getSharedPreferences(
            "$PREF_NAME ${currentRocket.rocketId}",
            Context.MODE_PRIVATE
        )
        val scrollPosition = sharedPreferences.getInt(
            "$KEY_SCROLL_POSITION ${currentRocket.rocketId}",
            DEFAULT_SCROLL_POSITION
        )
        binding.nestedScrollView.post {
            binding.nestedScrollView.scrollTo(
                DEFAULT_SCROLL_POSITION,
                scrollPosition
            )
        }
    }


    private fun bind() {
        loadImage()
        binding.rocketTitle.text = currentRocket.rocketName
        binding.firstLaunchDate.text = currentRocket.firstFlight.formatToRusDate()
        binding.country.text = when (currentRocket.country) {
            "Republic of the Marshall Islands" -> "Маршалловы острова"
            "United States" -> "США"
            else -> ""
        }
        binding.launchPrice.text = currentRocket.costPerLaunch.toShortPriceName()

        binding.enginesCountFirstStage.text = currentRocket.enginesCountFirstStage.toString()
        binding.fuelCountFirstStage.text = currentRocket.fuelAmountTonsFirstStage.toInt().toString()
        binding.burnTimeFirstStage.text = currentRocket.burnTimeSecFirstStage.toString()

        binding.enginesCountSecondStage.text = currentRocket.enginesCountSecondStage.toString()
        binding.fuelCountSecondStage.text =
            currentRocket.fuelAmountTonsSecondStage.toInt().toString()
        binding.burnTimeSecondStage.text = currentRocket.burnTimeSecSecondStage.toString()

        val adapter = RocketsFeaturesAdapter(featuresList)
        binding.rocketFeatureRv.adapter = adapter
    }

    private fun initClickListeners() {
        binding.checkLaunches.setOnClickListener {
            findNavController().navigate(
                RocketsViewPagerFragmentDirections.actionRocketsViewPagerFragmentToLaunchesFragment(
                    currentRocket.rocketName
                )
            )
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

    private fun loadSwitchValuesFromSharedPreferences() {
        val sharedPreferences =
            requireContext().getSharedPreferences(Constants.PREF_VALUES, Context.MODE_PRIVATE)
        featuresList = mutableListOf(
            RocketFeaturesModel(
                if (sharedPreferences.getBoolean(
                        Constants.HEIGHT,
                        Constants.FALSE
                    )
                ) "Высота, ft" else "Высота, m",
                if (sharedPreferences.getBoolean(
                        Constants.HEIGHT,
                        Constants.FALSE
                    )
                ) currentRocket.heightFeet.toString() else currentRocket.heightM.toString()
            ),
            RocketFeaturesModel(
                if (sharedPreferences.getBoolean(
                        Constants.DIAMETER,
                        Constants.FALSE
                    )
                ) "Диаметр, ft" else "Диаметр, m",
                if (sharedPreferences.getBoolean(
                        Constants.DIAMETER,
                        Constants.FALSE
                    )
                ) currentRocket.diameterFeet.toString() else currentRocket.diameterM.toString()
            ),
            RocketFeaturesModel(
                if (sharedPreferences.getBoolean(
                        Constants.MASS,
                        Constants.FALSE
                    )
                ) "Масса, lb" else "Масса, kg",
                if (sharedPreferences.getBoolean(
                        Constants.MASS,
                        Constants.FALSE
                    )
                ) currentRocket.massLb.toDouble()
                    .toEasyReadStringNumber() else currentRocket.massKg.toDouble()
                    .toEasyReadStringNumber()
            ),
            RocketFeaturesModel(
                if (sharedPreferences.getBoolean(
                        Constants.LEO,
                        Constants.FALSE
                    )
                ) "Нагрузка, lb" else "Нагрузка, kg",
                if (sharedPreferences.getBoolean(
                        Constants.LEO,
                        Constants.FALSE
                    )
                ) currentRocket.LeoPayloadLb.toDouble()
                    .toEasyReadStringNumber() else currentRocket.LeoPayloadKg.toDouble()
                    .toEasyReadStringNumber()
            )
        )
    }

    companion object {

        private const val CURRENT_ROCKET = "CURRENT_ROCKET"
        private const val NO_ROCKET_EXCEPTION = "No Rocket Found"
        private const val PREF_NAME = "SCROLL_Y"
        private const val KEY_SCROLL_POSITION = "KEY_SCROLL_POSITION"
        private const val DEFAULT_SCROLL_POSITION = 0

        @JvmStatic
        fun newInstance(currentRocket: Rocket) =
            RocketsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(CURRENT_ROCKET, currentRocket)
                }
            }
    }

    override fun onStop() {
        super.onStop()
        val sharedPreferences = requireContext().getSharedPreferences(
            "$PREF_NAME ${currentRocket.rocketId}", Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putInt(
            "$KEY_SCROLL_POSITION ${currentRocket.rocketId}",
            binding.nestedScrollView.scrollY
        )
        editor.apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}