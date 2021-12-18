package com.example.weather.weather_by_day

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.common.WeatherByDayAdapter
import com.example.weather.common.configurations.App
import java.text.SimpleDateFormat
import java.util.*

private val TAG = WeatherByDayFragment::class.simpleName

private const val EXTRA_CITY_ID = "city_id"
private const val EXTRA_DATE = "date"

fun WeatherByDayFragment(cityId: Int, date: Long): WeatherByDayFragment = WeatherByDayFragment.create(cityId, date)

class WeatherByDayFragment private constructor() : Fragment() {
    private val cityId: Int get() = arguments?.getInt(EXTRA_CITY_ID)!!
    private val date: Date get() = Date(arguments?.getLong(EXTRA_DATE)!!)

    private val viewModel: DetailedDayWeatherViewModel by viewModels {
        App.getComponent().detailedDayWeatherViewModelFactory.apply {
            setCityId(cityId)
            setDate(date)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_weather_by_day, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.i(TAG, "Load daily weather for cityId: " + cityId + " and date: " +
                SimpleDateFormat("E yyyy.MM.dd").format(date))

        setupOnDayRecycler()
    }

    private fun setupOnDayRecycler() {
        val weatherRecycler: RecyclerView = requireView().findViewById(R.id.on_day_recycler)
        val adapter = WeatherByDayAdapter(ArrayList())
        weatherRecycler.adapter = adapter
        viewModel.detailedWeathers.observe(viewLifecycleOwner) { dayWeathers -> adapter.setItems(dayWeathers) }
        weatherRecycler.layoutManager = LinearLayoutManager(requireContext())
    }

    companion object {
        fun create(cityId: Int, date: Long): WeatherByDayFragment {
            val fragment = WeatherByDayFragment()
            val bundle = Bundle()
            bundle.putInt(EXTRA_CITY_ID, cityId)
            bundle.putLong(EXTRA_DATE, date)
            fragment.arguments = bundle
            return fragment
        }
    }
}