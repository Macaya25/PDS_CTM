package cl.uandes.pichangapp.ui.view.search_match


import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerFragment(val listener: (hour:Int, minute:Int) -> Unit): DialogFragment(), TimePickerDialog.OnTimeSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(requireContext(), this, hour, minute, DateFormat.is24HourFormat(requireContext()))
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        listener(hourOfDay,minute)
        // Do something with the time chosen by the user
    }
}
