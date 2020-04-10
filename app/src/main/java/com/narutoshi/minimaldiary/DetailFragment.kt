package com.narutoshi.minimaldiary

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        menu?.apply {
            findItem(R.id.action_delete).isVisible = true
            findItem(R.id.action_edit).isVisible = true
            findItem(R.id.action_register).isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_delete -> {
                onDeleteBtnClicked(date, diaryDetail)
            }

            R.id.action_edit -> listener?.onEditBtnSelected(date!!, diaryDetail!!)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun onDeleteBtnClicked(date: String?, diaryDetail: String?) {

        val realm = Realm.getDefaultInstance()
        val selectedTodo = realm.where(DiaryModel::class.java)
            .equalTo(DiaryModel::date.name, date)
            .equalTo(DiaryModel::diaryDetail.name, diaryDetail)
            .findFirst()
        realm.beginTransaction() // この文書き忘れてエラー起こした
        selectedTodo?.deleteFromRealm()
        realm.commitTransaction()
        realm.close()

        listener?.onDataDeleted()
        fragmentManager!!.beginTransaction().remove(this).commit()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dateDetail.text = date
        diaryContentDetail.text = diaryDetail

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
        fun onDataDeleted()
        fun onEditBtnSelected(date: String, diaryDetail: String)
    }

    companion object {

        @JvmStatic
        fun newInstance(date: String, diaryDetail: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(IntentKey.DATE.name, date)
                    putString(IntentKey.DIARY_DETAIL.name, diaryDetail)
                }
            }
    }
}
