package konstmih.app.weatherapp.weather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.squareup.picasso.Picasso
import konstmih.app.weatherapp.App
import konstmih.app.weatherapp.R
import konstmih.app.weatherapp.openweathermap.WeatherWrapper
import konstmih.app.weatherapp.openweathermap.mapOpenWeatherDataToWeather
import konstmih.app.weatherapp.utils.toast
import retrofit2.Call
import retrofit2.Response

class WeatherFragment : Fragment() {

    private lateinit var cityName: String
    private val TAG = WeatherFragment::class.java.simpleName // TAG for Log

    // From layout fragment_weather.xml
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var city: TextView
    private lateinit var weatherIcon: ImageView
    private lateinit var weatherDescription: TextView
    private lateinit var temperature: TextView
    private lateinit var humidity: TextView
    private lateinit var pressure: TextView

    companion object {
        val EXTRA_CITY_NAME = "konstmih.app.weatherapp.extras.EXTRA_CITY_NAME"
        fun newInstance() = WeatherFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflating XML layout file to get the view
        val view = inflater.inflate(R.layout.fragment_weather, container, false)

        refreshLayout = view.findViewById(R.id.swipe_refresh)
        city = view.findViewById(R.id.city)
        weatherIcon = view.findViewById(R.id.weather_icon)
        weatherDescription = view.findViewById(R.id.weather_description)
        temperature = view.findViewById(R.id.temperature)
        humidity = view.findViewById(R.id.humidity)
        pressure = view.findViewById(R.id.pressure)

        // Refresh listener
        refreshLayout.setOnRefreshListener { refreshWeather() }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity?.intent!!.hasExtra(EXTRA_CITY_NAME))
            updateWeatherForCity(activity!!.intent.getStringExtra(EXTRA_CITY_NAME))
    }

    public fun updateWeatherForCity(cityName: String) {
        // Displays the city name on the top of the weather page
        this.cityName = cityName
        this.city.text = cityName

        if (!refreshLayout.isRefreshing) {
            refreshLayout.isRefreshing = true
        }

        // Query call to get the weather from online service
        val call = App.weatherService.getWeather("$cityName,fr")
        call.enqueue(object : retrofit2.Callback<WeatherWrapper> {
            override fun onFailure(call: Call<WeatherWrapper>, t: Throwable) {
                Log.e(TAG, getString(R.string.weather_message_error_could_not_load_weather), t)
                context?.toast(getString(R.string.weather_message_error_could_not_load_weather))
                refreshLayout.isRefreshing = false
            }

            override fun onResponse(
                call: Call<WeatherWrapper>,
                response: Response<WeatherWrapper>
            ) {
                response?.body().let {
                    val weather = mapOpenWeatherDataToWeather(it!!)
                    updateUi(weather)
                    Log.i(TAG, "Weather response: $weather")
                    refreshLayout.isRefreshing = false
                }

            }

        })
    }

    private fun updateUi(weather: Weather) {

        // Icon
        Picasso.get()
            .load(weather.iconUrl)
            .placeholder(R.drawable.ic_cloud_off_black_24dp)
            .into(weatherIcon)

        // Info
        weatherDescription.text = weather.description
        temperature.text =
            getString(R.string.weather_temperature_value, weather.temperature.toInt())
        humidity.text = getString(R.string.weather_humidity_value, weather.humidity)
        pressure.text = getString(R.string.weather_pressure_value, weather.pressure)
    }

    private fun refreshWeather() {
        updateWeatherForCity(cityName)
    }

    fun clearUi() {
        weatherIcon.setImageResource(R.drawable.ic_cloud_off_black_24dp)
        cityName = ""
        city.text = ""
        weatherDescription.text = ""
        temperature.text = ""
        humidity.text = ""
        pressure.text = ""
    }
}