package konstmih.app.weatherapp.city

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import konstmih.app.weatherapp.R

class CityAdapter(private val cities: List<City>,
                  private val cityListener: CityAdapter.CityItemListener)
    :RecyclerView.Adapter<CityAdapter.ViewHolder>(), View.OnClickListener {

    // Interface
    interface CityItemListener {
        fun onCitySelected(city:City)
        fun onCityDeleted(city: City)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Views
        val cardView = itemView.findViewById<CardView>(R.id.card_view)
        val cityNameView = itemView.findViewById<TextView>(R.id.name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflating XML layout
        val viewItem = LayoutInflater.from(parent?.context)
            .inflate(R.layout.item_city, parent, false)
        // Returning a view holder based on an item_city
        return ViewHolder(viewItem)
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = cities[position]
        with(holder){
            // CardView update
            cardView.tag = city
            // Setting a click listener to be select a city
            cardView.setOnClickListener(this@CityAdapter)

            // Update city name
            cityNameView.text = city.name
        }
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            // If clicked on the card view, means that we clicked on a city
            R.id.card_view -> cityListener.onCitySelected(view.tag as City)
        }
    }
}