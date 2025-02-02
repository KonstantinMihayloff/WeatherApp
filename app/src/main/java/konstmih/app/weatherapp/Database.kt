package konstmih.app.weatherapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import konstmih.app.weatherapp.city.City

// Database Constants
private const val DATABASE_NAME = "weather.db"
private const val DATABASE_VERSION = 1

// Table Constants
private const val CITY_TABLE_NAME = "city"
private const val CITY_KEY_ID = "id"
private const val CITY_KEY_NAME = "name"

// SQL
private const val CITY_TABLE_CREATE = """
CREATE TABLE $CITY_TABLE_NAME (
    $CITY_KEY_ID INTEGER PRIMARY KEY,
    $CITY_KEY_NAME TEXT
)
"""

private const val CITY_QUERY_SELECT_ALL = "SELECT * FROM $CITY_TABLE_NAME"


class Database(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    private val TAG = Database::class.java.simpleName

    override fun onCreate(db: SQLiteDatabase?) {
        // Create Table
        db?.execSQL(CITY_TABLE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun createCity(city: City) : Boolean {
        // values creation
        val values = ContentValues()

        // Filling values
        values.put(CITY_KEY_NAME, city.name)

        // Log
        Log.d(TAG, "Creating city: $values")

        // Id is returned by writableDatabase.insert function. That is how we get the city id
        val id = writableDatabase.insert(CITY_TABLE_NAME, null, values)
        city.id = id

        return id > 0
    }

    fun deleteCity(city: City): Boolean {
        // Log
        Log.d(TAG,"Delete city $city")

        // Only 1 line should be deleted
        // Request that means DELETE FROM city WHERE id = 1 for example
        val deleteCount = writableDatabase.delete(
            CITY_TABLE_NAME,
            "$CITY_KEY_ID = ?",
            arrayOf("${city.id}")
        )

        return deleteCount ==1
    }

    fun getAllCities(): MutableList<City> {
        val cities = mutableListOf<City>()

        // SQL Query to get all the cities
        readableDatabase.rawQuery(CITY_QUERY_SELECT_ALL, null).use{cursor ->
            while (cursor.moveToNext()) {
                // Get all cities with cursor
                val city = City(
                    cursor.getLong(cursor.getColumnIndex(CITY_KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(CITY_KEY_NAME))
                )

                cities.add(city)
            }
        }

        return cities
    }
}