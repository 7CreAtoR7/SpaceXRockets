package ru.ilya.spacexrockets.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch
import ru.ilya.spacexrockets.R
import ru.ilya.spacexrockets.databinding.ActivityMainBinding
import ru.ilya.spacexrockets.presentation.rockets_screen.RocketsPagerAdapter
import ru.ilya.spacexrockets.util.AppSpaceX
import ru.ilya.spacexrockets.util.RocketsUIState
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as AppSpaceX).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        initObservers()
    }


    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is RocketsUIState.Loading -> {
                        binding.progressLoader.visibility = View.VISIBLE
                    }
                    is RocketsUIState.Success -> {
                        binding.progressLoader.visibility = View.GONE
                        binding.viewPager.adapter = RocketsPagerAdapter(supportFragmentManager, state.rocketsList)
                        binding.dotsIndicator.attachTo(binding.viewPager) // dots indicator
                    }
                    is RocketsUIState.Error -> {
                        binding.progressLoader.visibility = View.GONE
                        Toast.makeText(this@MainActivity, state.message, Toast.LENGTH_LONG).show()
                    }
                    is RocketsUIState.Init -> {
                        viewModel.getRockets()
                    }
                }
            }
        }
    }
}