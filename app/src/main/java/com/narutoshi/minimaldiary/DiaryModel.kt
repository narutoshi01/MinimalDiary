package com.narutoshi.minimaldiary

import io.realm.RealmObject

open class DiaryModel : RealmObject() {

    var date: String = ""

    var diaryDetail: String = ""
}