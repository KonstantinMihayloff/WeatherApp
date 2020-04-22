package konstmih.app.weatherapp.city

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import konstmih.app.weatherapp.R

class DeleteCityDialogFragment : DialogFragment() {

    // Listener for Dialog Fragment
    interface DeleteCityDialogListener {
        fun onDialogPositiveClick() // Positive Click
        fun onDialogNegativeClick() // Negative Click
    }

    companion object{

        val EXTRA_CITY_NAME = "konstmih.app.weatherapp.extras.EXTRA_CITY_NAME"

        fun newInstance(cityName: String) : DeleteCityDialogFragment {

            // Init fragment
            val fragment = DeleteCityDialogFragment()

            // Setting as arguments the name of the city to be deleted
            fragment.arguments = Bundle().apply{
                putString(EXTRA_CITY_NAME, cityName)
            }

            return fragment
        }
    }

    // Listener
    var listener : DeleteCityDialogListener? = null
    // City name to be deleted
    private lateinit var cityName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get city to be deleted
        cityName = arguments?.getString(EXTRA_CITY_NAME)!!
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Builder Creation
        val builder = AlertDialog.Builder(context)


        // Builder Management
        builder.setTitle(getString(R.string.deletecity_title, cityName))
            .setPositiveButton(getString(R.string.deletecity_positive),
                DialogInterface.OnClickListener { _, _ ->
                    listener?.onDialogPositiveClick() })
            .setNegativeButton(getString(R.string.deletecity_negative),
                DialogInterface.OnClickListener {dialog, _ ->
                    dialog.cancel()
                    listener?.onDialogNegativeClick()
                })

        return builder.create()
    }
}