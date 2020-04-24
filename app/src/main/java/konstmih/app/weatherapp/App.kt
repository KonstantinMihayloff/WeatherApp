package konstmih.app.weatherapp

import android.app.Application
import konstmih.app.weatherapp.openweathermap.OpenWeatherService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    companion object{
        lateinit var instance: App // Application instance

        val database: Database by lazy { // Database
            Database(instance)
        }

        // HTTP Client
        private val httpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        // Retrofit Client
        private val retrofit = Retrofit.Builder()
            .client(httpClient)
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Weaather Service
        val weatherService: OpenWeatherService = retrofit.create(OpenWeatherService::class.java)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this // Init of application
    }
}