package ru.ilya.spacexrockets.presentation.launches_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.ilya.spacexrockets.databinding.FragmentLaunchesBinding
import ru.ilya.spacexrockets.presentation.launches_screen.launches_adapter.LaunchesAdapter

@AndroidEntryPoint
class LaunchesFragment : Fragment() {

    private var _binding: FragmentLaunchesBinding? = null
    private val binding: FragmentLaunchesBinding
        get() = _binding ?: FragmentLaunchesBinding.inflate(layoutInflater).also { _binding = it }

    private val args by navArgs<LaunchesFragmentArgs>()
    private val viewModel: LaunchesViewModel by viewModels()
    private val launchesAdapter: LaunchesAdapter by lazy { LaunchesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLaunchesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        initObservers()
        initClickListeners()
    }

    private fun setupRecyclerView() {
        with(binding.launchesRv) {
            layoutManager = LinearLayoutManager(activity)
            adapter = launchesAdapter
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is LaunchesUIState.Loading -> {
                        binding.progressLoader.visibility = View.VISIBLE
                        if (state.launchesListFromLastSession.isNotEmpty()) {
                            // есть сохраненные данные с прошлой сессии, пока отобразим их
                            launchesAdapter.submitList(state.launchesListFromLastSession)
                            binding.progressLoader.visibility = View.GONE
                        }
                    }
                    is LaunchesUIState.Success -> {
                        binding.progressLoader.visibility = View.GONE
                        if (state.launchesList.isNotEmpty())
                            launchesAdapter.submitList(state.launchesList)
                        else
                            binding.isEmptyList.visibility = View.VISIBLE
                    }
                    is LaunchesUIState.Error -> {
                        binding.progressLoader.visibility = View.GONE
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                    }
                    is LaunchesUIState.Init -> {
                        binding.rocketName.text = args.rocketName
                        viewModel.getLaunchesByRocketName(rocketName = args.rocketName)
                    }
                }
            }
        }
    }

    private fun initClickListeners() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}