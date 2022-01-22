package com.example.weather.weather_by_week

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView.OnItemClickListener
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.weather.R
import com.example.weather.common.WeatherByDayAdapter
import com.example.weather.common.configurations.App
import com.example.weather.common.models.DayWeather
import com.example.weather.databinding.FragmentWeatherByWeekBinding
import com.example.weather.weather_by_day.WeatherByDayFragment
import com.example.weather.weather_by_week.data.local.City

class WeatherByWeekFragment : Fragment() {
    private val viewModel: WeatherViewModel by viewModels(
        factoryProducer = App.getComponent()::getWeatherViewModelFactory
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createBinding()
            .setupTopBar()
            .setupWeatherRecycler()
            .setupCity()
    }

    private fun createBinding(): FragmentWeatherByWeekBinding {
        val binding: FragmentWeatherByWeekBinding =
            DataBindingUtil.setContentView(requireActivity(), R.layout.fragment_weather_by_week)
        binding.weather = viewModel
        return binding
    }

    private fun FragmentWeatherByWeekBinding.setupTopBar(): FragmentWeatherByWeekBinding {
        topBar.setOnClickListener {
            gotoWeatherByDay(viewModel.cityId, viewModel.date.time)
        }
        return this
    }

    private fun FragmentWeatherByWeekBinding.setupWeatherRecycler(): FragmentWeatherByWeekBinding {
        val adapter = WeatherByDayAdapter(ArrayList())
        weatherRecycler.adapter = adapter
        viewModel.sixteenDayWeathers.observe(this@WeatherByWeekFragment) { dayWeathers ->
            adapter.setItems(dayWeathers.map(::setupCellClickListener))
        }
        return this
    }

    private fun setupCellClickListener(dayWeather: DayWeather): DayWeather {
        dayWeather.setGotoDetailedDayWeather { cityId, date ->
            gotoWeatherByDay(cityId, date.time)
        }
        return dayWeather
    }

    private fun gotoWeatherByDay(cityId: Int, date: Long) {
        findNavController().navigate(
            R.id.action_weatherByWeekFragment_to_weatherByDayFragment,
            bundleOf(
                WeatherByDayFragment.EXTRA_CITY_ID to cityId,
                WeatherByDayFragment.EXTRA_DATE to date
            )
        )
    }

    private fun FragmentWeatherByWeekBinding.setupCity(): FragmentWeatherByWeekBinding {
        city.threshold = 4
        city.setAdapter(CitiesAdapter(requireContext(), App.getComponent().citesDao))
        city.onItemClickListener = OnItemClickListener { adapterView, view, position, id ->
            val city = adapterView.getItemAtPosition(position) as City
            this.city.setText(city.name)
            viewModel.setCityId(city.id)
            viewModel.updateCityWeight(city)

            // Скроем экранную клавиатуру
            val inputMethodManager =
                getSystemService(requireContext(), InputMethodManager::class.java) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(adapterView.applicationWindowToken, 0)

            // Очищаем фокус
            this.city.clearFocus()
        }
        return this
    }
}