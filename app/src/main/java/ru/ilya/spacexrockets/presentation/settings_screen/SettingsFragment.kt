package ru.ilya.spacexrockets.presentation.settings_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import ru.ilya.spacexrockets.R
import ru.ilya.spacexrockets.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding
        get() = _binding ?: throw RuntimeException("FragmentSettingsBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).supportActionBar?.hide()
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSwitchersListener()
    }

    private fun initSwitchersListener() {
        binding.switchHeight.setOnCheckedChangeListener { _, checked ->
            when {
                checked -> {
                    binding.switchHeightM.setTextColor(ContextCompat.getColor(requireContext(),R.color.grey))
                    binding.switchHeightFt.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
                }
                else -> {
                    binding.switchHeightM.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
                    binding.switchHeightFt.setTextColor(ContextCompat.getColor(requireContext(),R.color.grey))
                }
            }
        }

        binding.switchDiameter.setOnCheckedChangeListener { _, checked ->
            when {
                checked -> {
                    binding.switchDiameterM.setTextColor(ContextCompat.getColor(requireContext(),R.color.grey))
                    binding.switchDiameterFt.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
                }
                else -> {
                    binding.switchDiameterM.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
                    binding.switchDiameterFt.setTextColor(ContextCompat.getColor(requireContext(),R.color.grey))
                }
            }
        }

        binding.switchMass.setOnCheckedChangeListener { _, checked ->
            when {
                checked -> {
                    binding.switchMassKg.setTextColor(ContextCompat.getColor(requireContext(),R.color.grey))
                    binding.switchMassLb.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
                }
                else -> {
                    binding.switchMassKg.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
                    binding.switchMassLb.setTextColor(ContextCompat.getColor(requireContext(),R.color.grey))
                }
            }
        }

        binding.switchLeo.setOnCheckedChangeListener { _, checked ->
            when {
                checked -> {
                    binding.switchLeoKg.setTextColor(ContextCompat.getColor(requireContext(),R.color.grey))
                    binding.switchLeoLb.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
                }
                else -> {
                    binding.switchLeoKg.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
                    binding.switchLeoLb.setTextColor(ContextCompat.getColor(requireContext(),R.color.grey))
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}