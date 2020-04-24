package konstmih.app.weatherapp.weather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import konstmih.app.weatherapp.App
import konstmih.app.weatherapp.R
import konstmih.app.weatherapp.openweathermap.WeatherWrapper
import konstmih.app.weatherapp.openweathermap.mapOpenWeatherDataToWeather
import konstmih.app.weatherapp.utils.toast
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class WeatherFragment : Fragment() {

    private lateinit var cityName: String
    private val TAG= WeatherFragment::class.java.simpleName

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

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(activity?.intent!!.hasExtra(EXTRA_CITY_NAME))
            updateWeatherForCity(activity!!.intent.getStringExtra(EXTRA_CITY_NAME))
    }

    private fun updateWeatherForCity(cityName: String) {
        this.cityName = cityName

        val call = App.weatherService.getWeather("$cityName,fr")
        call.enqueue(object: retrofit2.Callback<WeatherWrapper> {
            override fun onFailure(call: Call<WeatherWrapper>, t: Throwable) {
                Log.e(TAG, getString(R.string.weather_message_error_could_not_load_weather), t)
                context?.toast(getString(R.string.weather_message_error_could_not_load_weather))
            }

            override fun onResponse(
                call: Call<WeatherWrapper>,
                response: Response<WeatherWrapper>
            ) {
                response?.body().let {
                    val weather = mapOpenWeatherDataToWeather(it!!)
                    Log.i(TAG, "Weather response: $weather")
                }

            }

        })
    }
}