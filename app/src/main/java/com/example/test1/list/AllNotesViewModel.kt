package com.example.test1.list


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.test1.NoteInteractor
import com.example.test1.SingleLiveEvent
import com.example.test1.database.NoteData
import com.example.test1.database.NoteDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect


class AllNotesViewModel(
    private val database: NoteDatabase
) : ViewModel() {
    val notes = MutableLiveData<List<NoteData>>()
    val openNote = SingleLiveEvent<Pair<List<NoteData>, Int>>()
    val openAbout = SingleLiveEvent<Unit>()

    fun loadAllNotes() {
        viewModelScope.launch {
            loadNotes().collect {
                notes.postValue(it)
            }
        }
    }

    fun btnAboutActivityClick() {
        openAbout.call()
    }

    fun openNote(notes: List<NoteData>, currentPosition: Int) {
        openNote.postValue(notes to currentPosition)
    }

    fun createNote() {
        val newNote = NoteData(0, "", "")
        addNote(newNote)
    }

    fun downloadNote() {
        viewModelScope.launch {
            val noteData: NoteData = NoteInteractor().getNote()
            val newNote = NoteData(0, noteData.title, noteData.text)
            addNote(newNote)
        }
    }

    private fun addNote(newNote: NoteData) {
        val newNoteList = mutableListOf<NoteData>().apply {
            addAll(notes.value ?: listOf())
            add(newNote)
        }
        notes.postValue(newNoteList)
        openNote(newNoteList, newNoteList.size - 1)
    }

    private fun loadNotes(): Flow<List<NoteData>> = database.noteDao().getAll()
}

@Suppress("UNCHECKED_CAST")
class AllNotesViewModelFactory(
    private val database: NoteDatabase
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        AllNotesViewModel(database) as T
}
