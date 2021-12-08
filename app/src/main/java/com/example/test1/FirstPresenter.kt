package com.example.test1

class FirstPresenter(private val mainUi: FirstMainUi) {

    fun activityBtnClick() {
        mainUi.startIntent()

    }


}
