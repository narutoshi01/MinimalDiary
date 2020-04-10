package com.narutoshi.minimaldiary

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.narutoshi.minimaldiary.MainFragment.OnListFragmentInteractionListener
import kotlinx.android.synthetic.main.fragment_main.view.*

class MyMainRecyclerViewAdapter(
    private val mValues: List<DiaryModel>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MyMainRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as DiaryModel

            mListener?.onListItemClicked(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_main, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // mValues : DiaryListのこと
        // Listの１つ１つのカードに表示させるViewの中身の設定
        val item = mValues[position]
        holder.cardTxtDate.text = item.date

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        // mView: CardViewのこと
        // カードビュー内に表示させるアイテムに対するレファレンスを持つようなイメージかな？

        val cardTxtDate: TextView = mView.cardTxtDate
    }
}
