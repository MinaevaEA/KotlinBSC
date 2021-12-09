package com.example.test1

class ListPresenter(private val mainView: ListMainView) {

    private var notes = mutableListOf<NoteData>()

    fun btnAboutActivityClick() {
        mainView.openActivityAbout()
    }

    fun openNote(noteData: NoteData) {

        mainView.onNoteOpen(noteData)
    }

    init {
        for (i in 0 until 100) {
            val note = NoteData(title = String(), text = String())
            note.title = "Заголовок #$i"
            note.text = "Текст #$i"
            notes += note
        }
        mainView.showNoteList(notes)
    }

}
