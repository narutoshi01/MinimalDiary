package com.narutoshi.minimaldiary

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_edit.*

class EditFragment : Fragment() {

    private var date: String? = null
    private var diaryDetail: String? = null


    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            date = it.getString(IntentKey.DATE.name)
            diaryDetail = it.getString(IntentKey.DIARY_DETAIL.name)
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
            // Todo registerボタンが押されたときのクリック処理（Realmに新規登録 or 更新，コールバックしてリストの更新）
        }

        return super.onOptionsItemSelected(item)
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
    }

    companion object {
        @JvmStatic
        fun newInstance(date: String, diaryDetail: String) =
            EditFragment().apply {
                arguments = Bundle().apply {
                    putString(IntentKey.DATE.name, date)
                    putString(IntentKey.DIARY_DETAIL.name, diaryDetail)
                }
            }
    }
}
