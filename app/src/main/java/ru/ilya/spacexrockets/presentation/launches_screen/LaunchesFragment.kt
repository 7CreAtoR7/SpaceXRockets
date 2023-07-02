package ru.ilya.spacexrockets.presentation.launches_screen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ru.ilya.spacexrockets.databinding.FragmentLaunchesBinding
import ru.ilya.spacexrockets.presentation.launches_screen.launches_adapter.LaunchesAdapter
import ru.ilya.spacexrockets.util.AppSpaceX
import ru.ilya.spacexrockets.util.ViewModelFactory
import javax.inject.Inject

class LaunchesFragment : Fragment() {

    private var _binding: FragmentLaunchesBinding? = null
    private val binding: FragmentLaunchesBinding
        get() = _binding ?: throw RuntimeException("FragmentLaunchesBinding == null")

    private val args by navArgs<LaunchesFragmentArgs>()
    private lateinit var viewModel: LaunchesViewModel
    private val launchesAdapter: LaunchesAdapter by lazy { LaunchesAdapter() }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as AppSpaceX).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLaunchesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[LaunchesViewModel::class.java]

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
                        viewModel.getLaunchesByRocketId(rocketName = args.rocketName)
                    }
                }
            }
        }
    }

    private fun initClickListeners() {
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}