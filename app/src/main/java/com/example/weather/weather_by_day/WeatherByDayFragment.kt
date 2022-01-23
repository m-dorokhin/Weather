package com.example.weather.weather_by_day

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.common.WeatherByDayAdapter
import com.example.weather.common.configurations.App
import java.text.SimpleDateFormat
import java.util.*

private val TAG = WeatherByDayFragment::class.simpleName

class WeatherByDayFragment : Fragment() {
    private val cityId: Int get() = arguments?.getInt(EXTRA_CITY_ID)!!
    private val date: Date get() = Date(arguments?.getLong(EXTRA_DATE)!!)

    private val viewModel: DetailedDayWeatherViewModel by viewModels {
        App.getComponent().detailedDayWeatherViewModelFactory
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_weather_by_day, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.i(TAG, "Load daily weather for cityId: " + cityId + " and date: " +
                SimpleDateFormat("E yyyy.MM.dd").format(date))

        setupOnDayRecycler()
        viewModel.getWeather(cityId, date)
    }

    private fun setupOnDayRecycler() {
        val weatherRecycler: RecyclerView = requireView().findViewById(R.id.on_day_recycler)
        val adapter = WeatherByDayAdapter(ArrayList())
        weatherRecycler.adapter = adapter
        viewModel.detailedWeathers.observe(viewLifecycleOwner) { dayWeathers -> adapter.setItems(dayWeathers) }
    }

    companion object {
        const val EXTRA_CITY_ID = "city_id"
        const val EXTRA_DATE = "date"
    }
}