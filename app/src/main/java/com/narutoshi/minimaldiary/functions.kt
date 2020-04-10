package com.narutoshi.minimaldiary

import androidx.fragment.app.FragmentManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*

fun getStringToday() : String {
    return SimpleDateFormat("yyyy/MM/dd").format(Date())
}

fun setToolBar(toolbar: androidx.appcompat.widget.Toolbar, supportFragmentManager: FragmentManager, fab: FloatingActionButton) {
    toolbar.apply {
        setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        setNavigationOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container_master,
                    MainFragment.newInstance(1),
                    FragmentTag.MAIN.toString()
                )
                .commit()
            showFab(fab)
            removeToolBar(toolbar)
        }
    }
}

fun removeToolBar(toolbar: androidx.appcompat.widget.Toolbar) {
    toolbar.navigationIcon = null
}

fun hideFab(fab: FloatingActionButton) {
    fab.hide()
}

fun showFab(fab: FloatingActionButton) {
    fab.show()
}
