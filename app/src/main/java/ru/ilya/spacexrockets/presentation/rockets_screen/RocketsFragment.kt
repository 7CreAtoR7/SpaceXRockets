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
import ru.ilya.spacexrockets.util.Constants.DIAMETER
import ru.ilya.spacexrockets.util.Constants.FALSE
import ru.ilya.spacexrockets.util.Constants.HEIGHT
import ru.ilya.spacexrockets.util.Constants.LEO
import ru.ilya.spacexrockets.util.Constants.MASS
import ru.ilya.spacexrockets.util.Constants.PREF_VALUES

class RocketsFragment : Fragment() {

    private var _binding: FragmentRocketsBinding? = null
    private val binding: FragmentRocketsBinding
        get() = _binding ?: throw RuntimeException("FragmentRocketsBinding == null")

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

    private fun loadSwitchValuesFromSharedPreferences() {
        val sharedPreferences = requireContext().getSharedPreferences(PREF_VALUES, Context.MODE_PRIVATE)
        featuresList = mutableListOf(
            RocketFeaturesModel(if (sharedPreferences.getBoolean(HEIGHT, FALSE)) "Высота, ft" else "Высота, m", if (sharedPreferences.getBoolean(HEIGHT, FALSE)) currentRocket.heightFeet else currentRocket.heightM),
            RocketFeaturesModel(if (sharedPreferences.getBoolean(DIAMETER, FALSE)) "Диаметр, ft" else "Диаметр, m", if (sharedPreferences.getBoolean(DIAMETER, FALSE)) currentRocket.diameterFeet else currentRocket.diameterM),
            RocketFeaturesModel(if (sharedPreferences.getBoolean(MASS, FALSE)) "Масса, lb" else "Масса, kg", if (sharedPreferences.getBoolean(MASS, FALSE)) currentRocket.massLb.toDouble() else currentRocket.massKg.toDouble()),
            RocketFeaturesModel(if (sharedPreferences.getBoolean(LEO, FALSE)) "Нагрузка, lb" else "Нагрузка, kg", if (sharedPreferences.getBoolean(LEO, FALSE)) currentRocket.LeoPayloadLb.toDouble() else currentRocket.LeoPayloadKg.toDouble())
        )
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