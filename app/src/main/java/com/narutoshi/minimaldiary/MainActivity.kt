package com.narutoshi.minimaldiary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.textfield.TextInputEditText

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), EditFragment.OnFragmentInteractionListener, DatePickerFragment.OnDateSetListener, MainFragment.OnListFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        toolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
            setNavigationOnClickListener {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container_master, MainFragment.newInstance(1), FragmentTag.MAIN.toString())
                    .commit()
            }
        }

        supportFragmentManager.beginTransaction()
            .add(R.id.container_master, MainFragment.newInstance(1), FragmentTag.MAIN.toString())
            .commit()

        fab.setOnClickListener { view ->
            // EditFragmentにいく　新規作成　
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_master, EditFragment.newInstance(getStringToday(), "", EditMode.NEW_ENTRY), FragmentTag.EDIT.toString())
                .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        menu.apply {
            findItem(R.id.action_delete).isVisible = false
            findItem(R.id.action_edit).isVisible = false
            findItem(R.id.action_register).isVisible = false
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    // EditFragment.OnFragmentInteractionListener#onDateSelectBtnClicked
    override fun onDateSelectBtnClicked() {
        // DatePicker開く
        DatePickerFragment().show(supportFragmentManager, FragmentTag.DATE_PICKER.toString())
    }

    // EditFragment.OnFragmentInteractionListener#onDataRecorded
    override fun onDataRecorded() {
        upDateDiaryList()
    }

    private fun upDateDiaryList() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_master, MainFragment.newInstance(1), FragmentTag.MAIN.toString())
            .commit()
    }

    // DatePickerFragment.OnDateSetListener#onDateSelected
    override fun onDateSelected(stringDate: String) {
        val dateEdit = findViewById<TextInputEditText>(R.id.dateEdit)
        dateEdit.setText(stringDate)
    }

    // MainFragment.OnListFragmentInteractionListener#onListItemClicked
    override fun onListItemClicked(item: DiaryModel) {
        //todo リスト内アイテムのクリック処理(DetailFragmentへ)
    }
}
