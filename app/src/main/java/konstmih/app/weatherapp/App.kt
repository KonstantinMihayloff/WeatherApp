package konstmih.app.weatherapp

import android.app.Application

class App : Application() {

    companion object{
        lateinit var instance: App // Application instance

        val database: Database by lazy { // Database
            Database(instance)
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this // Init of application
    }
}