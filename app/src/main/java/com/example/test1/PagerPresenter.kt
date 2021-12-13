package com.example.test1

class PagerPresenter(private val pagerView: PagerView) {

    fun saveNote(noteData: NoteData) {

        if (noteData.title.isEmpty() && noteData.text.isEmpty()) {
            pagerView.onNoteEmpty()
        } else {
            pagerView.onSaveSuccess()
        }
    }
}