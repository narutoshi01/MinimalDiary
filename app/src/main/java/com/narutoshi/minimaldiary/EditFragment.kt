package com.narutoshi.minimaldiary

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_edit.*
import java.text.ParseException
import java.text.SimpleDateFormat

class EditFragment : Fragment() {

    private var date: String? = null
    private var diaryDetail: String? = null
    private var mode: EditMode? = null

    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            date = it.getString(IntentKey.DATE.name)
            diaryDetail = it.getString(IntentKey.DIARY_DETAIL.name)
            mode = it.getSerializable(IntentKey.MODE_IN_EDIT.name) as EditMode
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        txtInputLayoutDate.hint = getStringToday()

        dateEdit.setText(date)
        diaryContentEdit.setText(diaryDetail)

        imgBtnDateSelect.setOnClickListener {
            listener?.onDateSelectBtnClicked()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        menu?.apply {
            findItem(R.id.action_delete).isVisible = false
            findItem(R.id.action_edit).isVisible = false
            findItem(R.id.action_register).isVisible = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.action_register) {
            recordToRealmDB(mode)
        }


        return super.onOptionsItemSelected(item)
    }

    private fun recordToRealmDB(mode: EditMode?) {
        if(!isDateValid()) {
            return
        }

        when(mode) {
            EditMode.NEW_ENTRY -> addNewDiary()
            EditMode.EDIT -> updateExistingDiary()
        }

        listener?.onDataRecorded()
        fragmentManager!!.beginTransaction().remove(this).commit()
    }

    private fun isDateValid(): Boolean {
        val userInputDate = dateEdit.text.toString()

        // 空欄かどうかtチェック
        if(userInputDate.isBlank()) {
            txtInputLayoutDate.error = "Date is required."
            return false
        }

        // フォーマットがyyyy/MM/dd型になっているか，ありえない日付になっていないか（2020/13/92など）チェック
        try {
            val format = SimpleDateFormat("yyyy/MM/dd")
            format.isLenient = false
            format.parse(userInputDate)
        } catch (e: ParseException) {
            txtInputLayoutDate.error = "Invalid Date."
            return false
        }

        return true
    }

    private fun addNewDiary() {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()

        val newDiary = realm.createObject(DiaryModel::class.java)
        newDiary.apply {
            date = dateEdit.text.toString()
            diaryDetail = diaryContentEdit.text.toString()
        }

        realm.commitTransaction()
        realm.close()
    }

    private fun updateExistingDiary() {
        // todo RealmDBの更新処理
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onDateSelectBtnClicked()
        fun onDataRecorded()
    }

    companion object {
        @JvmStatic
        fun newInstance(date: String, diaryDetail: String, mode: EditMode) =
            EditFragment().apply {
                arguments = Bundle().apply {
                    putString(IntentKey.DATE.name, date)
                    putString(IntentKey.DIARY_DETAIL.name, diaryDetail)
                    putSerializable(IntentKey.MODE_IN_EDIT.name, mode)
                }
            }
    }
}
