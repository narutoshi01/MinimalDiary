package com.narutoshi.minimaldiary

import io.realm.RealmObject

open class DiaryModel : RealmObject() {

    // yyyy/MM/dd
    var date: String = ""

    var diaryDetail: String = ""
}