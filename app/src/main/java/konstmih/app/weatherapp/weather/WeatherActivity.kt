package konstmih.app.weatherapp.weather

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import konstmih.app.weatherapp.R

class WeatherActivity : AppCompatActivity() {

    private val TAG = WeatherActivity::class.java.simpleName

    //override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i(TAG, "Activity is launched")

        // No layout file related to WeatherActivity: the weather fragment will be displayed
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, WeatherFragment.newInstance())
            .commit()
    }

}