package konstmih.app.weatherapp.city

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import konstmih.app.weatherapp.R

class CreateCityDialogFragment : DialogFragment() {

    // Listener for Dialog Fragment
    interface CreateCityDialogListener {
        fun onDialogPositiveClick(cityName: String) // Positive Click
        fun onDialogNegativeClick() // Negative Click
    }

    // Listener variable
    var listener: CreateCityDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Builder Creation
        val builder = AlertDialog.Builder(context)

        // EditText
        val input = EditText(context)
        with(input) {
            inputType = InputType.TYPE_CLASS_TEXT
            hint = context.getString(R.string.cratecity_cityhint)
        }

        // Builder Management
        builder.setTitle(getString(R.string.createcity_title))
            .setView(input)
            .setPositiveButton(R.string.createcity_positive,
                DialogInterface.OnClickListener {_, _ ->
                    listener?.onDialogPositiveClick(input.text.toString())
                })
            .setNegativeButton(R.string.createcity_negative ,
                DialogInterface.OnClickListener {dialog, _ ->
                    dialog.cancel()
                    listener?.onDialogNegativeClick()
                })

        return builder.create()
    }
}