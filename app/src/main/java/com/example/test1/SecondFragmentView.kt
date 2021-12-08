package com.example.test1

interface SecondFragmentView {
    fun onSaveFailed()
    fun onSaveSuccess()
    fun shareNote(title: String, message: String)
}