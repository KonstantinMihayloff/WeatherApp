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

class CityFragment : Fragment(), CityAdapter.CityItemListener {

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

    private fun saveCity(city: City)
    {
        if(database.createCity(city)) { // Adding city in database
            cities.add(city) // if success than it can be added to the mutableList cities
            adapter.notifyDataSetChanged()
        } else {
            Toast.makeText(context, "Could not create city", Toast.LENGTH_SHORT).show() // Error Toast
        }
    }

    override fun onCitySelected(city: City) {

    }

    override fun onCityDeleted(city: City) {

    }
}