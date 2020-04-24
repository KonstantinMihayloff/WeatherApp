package konstmih.app.weatherapp.openweathermap

import konstmih.app.weatherapp.weather.Weather

// Function to dissociate the structure of OpenWeatherMap and the structure of data used n the app

fun mapOpenWeatherDataToWeather(weatherWrapper: WeatherWrapper) : Weather {

    val weatherFirst = weatherWrapper.weather.first()
    return Weather(
        description = weatherFirst.description,
        temperature = weatherWrapper.main.temperature,
        humidity = weatherWrapper.main.humidity,
        pressure = weatherWrapper.main.pressure,
        iconUrl = "https://openweathermap.org/img/wn/${weatherFirst.icon}.png"
    )
}