package com.example.test1

class ListPresenter(private val mainView: ListMainView) {

    fun btnAboutActivityClick() {
        mainView.openActivityAbout()
    }
}
