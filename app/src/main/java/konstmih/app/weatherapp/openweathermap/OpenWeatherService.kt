package konstmih.app.weatherapp.openweathermap

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "bc60eb1c4230de68f31ac3afeaf7369c"

interface OpenWeatherService {

    // @Path for arguments before "?" ex: data/2.5/{id}/weather?units=metric&lang=fr
    // @Query for arguments after "?" ex: data/2.5/weather?q=London
    @GET("data/2.5/weather?units=metric&lang=fr")
    fun getWeather(@Query("q") cityName: String,
                   @Query("appid") apiKey: String = API_KEY)
            : Call<WeatherWrapper>

}