package com.narutoshi.minimaldiary

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.text.SimpleDateFormat
import java.util.*

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    var listener : OnDateSetListener? = null

    interface OnDateSetListener {
        fun onDateSelected(stringDate: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnDateSetListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null // 呼び出し元のアクティビティと離れる
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(activity!!, this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val dateString: String = getDateString(year, month, dayOfMonth)
        listener?.onDateSelected(dateString)
        fragmentManager?.beginTransaction()?.remove(this)?.commit()
    }

    private fun getDateString(year: Int, month: Int, dayOfMonth: Int): String {
        // Date型 -> String型へ変換
        val calender = Calendar.getInstance()
        calender.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("yyyy/MM/dd")
        return dateFormat.format(calender.time) // calender.timeはDate型を返す
    }
}