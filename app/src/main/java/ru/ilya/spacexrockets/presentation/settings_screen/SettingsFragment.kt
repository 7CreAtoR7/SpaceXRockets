package ru.ilya.spacexrockets.presentation.settings_screen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.ilya.spacexrockets.databinding.FragmentSettingsBinding
import ru.ilya.spacexrockets.util.Constants.DIAMETER
import ru.ilya.spacexrockets.util.Constants.FALSE
import ru.ilya.spacexrockets.util.Constants.HEIGHT
import ru.ilya.spacexrockets.util.Constants.LEO
import ru.ilya.spacexrockets.util.Constants.MASS
import ru.ilya.spacexrockets.util.Constants.PREF_VALUES

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding
        get() = _binding ?: throw RuntimeException("FragmentSettingsBinding == null")

    private var heightM: Boolean = FALSE
    private var diameterM: Boolean = FALSE
    private var massKg: Boolean = FALSE
    private var leoKg: Boolean = FALSE

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
        initClickListeners()
        loadSwitchValuesFromSharedPreferences()
        initSwitchValues()
    }

    private fun initClickListeners() {
        binding.close.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initSwitchValues() {
        binding.switchHeight.isChecked = heightM
        binding.switchDiameter.isChecked = diameterM
        binding.switchMass.isChecked = massKg
        binding.switchLeo.isChecked = leoKg
    }

    private fun saveSwitchValuesInSharedPreferences() {
        val sharedPref = requireContext().getSharedPreferences(PREF_VALUES, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean(HEIGHT, heightM)
        editor.putBoolean(DIAMETER, diameterM)
        editor.putBoolean(MASS, massKg)
        editor.putBoolean(LEO, leoKg)
        editor.apply()
    }

    private fun loadSwitchValuesFromSharedPreferences() {
        // загружаем статусы свитчеров из прошлой сессии
        val sharedPrefer = requireContext().getSharedPreferences(PREF_VALUES, Context.MODE_PRIVATE)
        heightM = sharedPrefer.getBoolean(HEIGHT, FALSE)
        diameterM = sharedPrefer.getBoolean(DIAMETER, FALSE)
        massKg = sharedPrefer.getBoolean(MASS, FALSE)
        leoKg = sharedPrefer.getBoolean(LEO, FALSE)
    }

    override fun onStop() {
        super.onStop()
        // сохраняем статусы чекеров в SP
        heightM = binding.switchHeight.isChecked
        diameterM = binding.switchDiameter.isChecked
        massKg = binding.switchMass.isChecked
        leoKg = binding.switchLeo.isChecked
        saveSwitchValuesInSharedPreferences()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}