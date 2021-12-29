package com.example.test1.list

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test1.database.NoteData
import com.example.test1.database.NoteDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class AllNotesViewModel(context: Context) : ViewModel() {
    val notes = MutableLiveData<List<NoteData>>()
    private val database = NoteDatabase.getDatabase(context)
    val openNote = MutableLiveData<Pair<List<NoteData>, Int>>()
    val openAbout = MutableLiveData<Unit>()
    fun loadAllNotes() {
        viewModelScope.launch {
            loadNotes().collect {
                notes.postValue(it)
            }
        }
    }

    private fun loadNotes(): Flow<List<NoteData>> = database.noteDao().getAll()

    fun btnAboutActivityClick() {
        openAbout.postValue(Unit)
    }

    fun openNote(notes: List<NoteData>, currentPosition: Int) {
        openNote.value = notes to currentPosition
    }

    fun createNote() {
        val newNote = NoteData(0, "", "")
        val newNoteList = mutableListOf<NoteData>().apply {
            addAll(notes.value ?: listOf())
            add(newNote)
        }
        notes.postValue(newNoteList)
        openNote(newNoteList, newNoteList.size - 1)
    }
}
