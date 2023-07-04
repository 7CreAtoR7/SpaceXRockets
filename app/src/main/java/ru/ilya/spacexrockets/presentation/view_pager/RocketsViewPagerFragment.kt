package ru.ilya.spacexrockets.presentation.view_pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.ilya.spacexrockets.databinding.FragmentRocketsViewPagerBinding
import ru.ilya.spacexrockets.presentation.rockets_screen.RocketsUIState

@AndroidEntryPoint
class RocketsViewPagerFragment : Fragment() {

    private var _binding: FragmentRocketsViewPagerBinding? = null
    private val binding: FragmentRocketsViewPagerBinding
        get() = _binding ?: FragmentRocketsViewPagerBinding.inflate(layoutInflater)
            .also { _binding = it }

    private val viewModel: RocketsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRocketsViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is RocketsUIState.Loading -> {
                        binding.progressLoader.visibility = View.VISIBLE
                        if (state.rocketsListFromLastSession.isNotEmpty()) {
                            // есть сохраненные данные с прошлой сессии, пока отобразим их
                            binding.viewPager.adapter = RocketsPagerAdapter(
                                childFragmentManager,
                                state.rocketsListFromLastSession,
                                lifecycle
                            )
                            binding.dotsIndicator.attachTo(binding.viewPager) // dots indicator
                            binding.progressLoader.visibility = View.GONE
                        }
                    }
                    is RocketsUIState.Success -> {
                        binding.progressLoader.visibility = View.GONE
                        val currentPosition = binding.viewPager.currentItem
                        binding.viewPager.adapter =
                            RocketsPagerAdapter(childFragmentManager, state.rocketsList, lifecycle)
                        binding.dotsIndicator.attachTo(binding.viewPager) // dots indicator
                        binding.viewPager.setCurrentItem(currentPosition, false)

                    }
                    is RocketsUIState.Error -> {
                        binding.progressLoader.visibility = View.GONE
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                    }
                    is RocketsUIState.Init -> {
                        viewModel.getRockets()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}