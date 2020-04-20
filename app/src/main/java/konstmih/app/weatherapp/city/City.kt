package konstmih.app.weatherapp.city

// Data Class City
data class City(var id: Long,
                var name: String) {

    // Constructor with default value for id
    constructor(name:String) : this(-1, name)
}