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
    private var currentCity : City? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)

        cityFragment = supportFragmentManager.findFragmentById(R.id.city_fragment) as CityFragment

        cityFragment.listener = this
    }

    override fun onCitySelected(city: City) {
        currentCity = city
        startWeatherActivity(city)
    }

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
