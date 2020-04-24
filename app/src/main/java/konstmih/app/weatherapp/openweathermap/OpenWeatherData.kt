package konstmih.app.weatherapp.openweathermap

import com.google.gson.annotations.SerializedName

data class WeatherWrapper(val weather: Array<WeatherData>, // List on OpenWeatherMap website
                          val main: MainData)

data class WeatherData (val description: String,
                        val icon: String)

data class MainData(@SerializedName("temp") val temperature: Float, // to associate temp with temperature variable
                    val pressure: Int,
                    val humidity: Int)