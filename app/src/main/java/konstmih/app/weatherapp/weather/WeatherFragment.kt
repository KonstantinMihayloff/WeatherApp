package konstmih.app.weatherapp.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import konstmih.app.weatherapp.R

class WeatherFragment : Fragment() {

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
}