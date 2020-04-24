package konstmih.app.weatherapp.city

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import konstmih.app.weatherapp.R
import konstmih.app.weatherapp.weather.WeatherActivity
import konstmih.app.weatherapp.weather.WeatherFragment

class CityActivity : AppCompatActivity(), CityFragment.CityFragmentListener {

    private lateinit var cityFragment: CityFragment

    private var weatherFragment: WeatherFragment? = null // Initialized to null as not used on phone, only for tablet
    private var currentCity : City? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)

        // Get fragment layout
        cityFragment = supportFragmentManager.findFragmentById(R.id.city_fragment) as CityFragment

        // Listener management
        cityFragment.listener = this

        // Weather fragment: TABLET ONLY
        // R.id.weather_fragment exists only for sw600dp
        weatherFragment = supportFragmentManager.findFragmentById(R.id.weather_fragment) as WeatherFragment
    }

    override fun onCitySelected(city: City) {
        currentCity = city

        if(isHandsetLayout()){ // If on a phone
            startWeatherActivity(city)
        } else {
            weatherFragment?.updateWeatherForCity(city.name)
        }

    }

    override fun onEmptyCities() {
        weatherFragment?.clearUi()
    }

    // If no weather fragment, then we are on smartphone
    private fun isHandsetLayout(): Boolean = weatherFragment == null

    private fun startWeatherActivity(city: City) {
        // Intent
        val intent = Intent(this, WeatherActivity::class.java)
        intent.action = Intent.ACTION_VIEW//TO REMOVE
        intent.addCategory("UserViewer")// TO REMOVE
        intent.putExtra(WeatherFragment.EXTRA_CITY_NAME, city.name)

        // Start activity
        startActivity(intent)
    }
}
