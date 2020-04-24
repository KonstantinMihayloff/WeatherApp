package konstmih.app.weatherapp.city

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import konstmih.app.weatherapp.App
import konstmih.app.weatherapp.Database
import konstmih.app.weatherapp.R
import konstmih.app.weatherapp.utils.toast

class CityFragment : Fragment(), CityAdapter.CityItemListener {

    // Listener for CityActivity
    interface CityFragmentListener {
        fun onCitySelected(city: City)
        fun onEmptyCities()
    }

    var listener: CityFragmentListener? = null

    private lateinit var cities: MutableList<City>
    private lateinit var database: Database
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Database
        database = App.database

        // Notify fragment that it has an option menu
        // So it will use onCreateOptionsMenu
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflating XML layout file to get the view
        val view = inflater.inflate(R.layout.fragment_city, container, false)

        // Recycler view
        recyclerView = view.findViewById(R.id.cities_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get cities from database
        cities = database.getAllCities()

        // Creating adapter
        adapter = CityAdapter(cities, this)

        // Linking recycler view and adapter
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflating XML layout file
        inflater.inflate(R.menu.fragment_city, menu)
    }

    // If item from options menu are selected
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.action_create_city -> {
                // Run CreateCity Dialog
                showCreateCityDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Creating dialog fragment
    private fun showCreateCityDialog() {
        val createCityFragment = CreateCityDialogFragment()

        // Listener interface
        createCityFragment.listener = object: CreateCityDialogFragment.CreateCityDialogListener{
            // Overriding interface listener functions
            override fun onDialogPositiveClick(cityName: String) {
                saveCity(City(cityName))
            }

            override fun onDialogNegativeClick() { }
        }

        // Display the dialog fragment
        fragmentManager?.let { createCityFragment.show(it, "CreateCityDialogFragment") }
    }

    private fun showDeleteCityDialog(city: City) {
        val deleteCityFragment = DeleteCityDialogFragment.newInstance(city.name)

        // Listener interface
        deleteCityFragment.listener = object: DeleteCityDialogFragment.DeleteCityDialogListener {
            override fun onDialogPositiveClick() {
                deleteCity(city)
            }

            override fun onDialogNegativeClick() { }

        }

        // Display the dialog fragment
        fragmentManager?.let { deleteCityFragment.show(it, "DeleteCityDialogFragment") }
    }

    private fun saveCity(city: City)
    {
        if(database.createCity(city)) { // Adding city in database
            cities.add(city) // if success than it can be added to the mutableList cities
            adapter.notifyDataSetChanged()
        } else {
            context?.toast(getString(R.string.city_message_error_could_not_create_city)) // ERROR
        }
    }

    private fun deleteCity(city: City) {
        if(database.deleteCity(city)) {
            cities.remove(city)
            adapter.notifyDataSetChanged()
            selectFirstCity() // Notify CityActivity that first city is selected
            context?.toast(getString(R.string.city_message_info_city_deleted, city.name)) // INFO
        } else {
            context?.toast(getString(R.string.city_message_error_could_not_delete_city, city.name)) // ERROR
        }
    }

    override fun onCitySelected(city: City) {
        listener?.onCitySelected(city)
    }

    override fun onCityDeleted(city: City) {
        showDeleteCityDialog(city)
    }

    // Function used to deal with the following issue :
    // On tablet, if current city is deleted, weather is still displayed
    fun selectFirstCity(){
        when(cities.isEmpty())
        {
            true ->listener?.onEmptyCities()
            false -> onCitySelected(cities.first())
        }
    }
}