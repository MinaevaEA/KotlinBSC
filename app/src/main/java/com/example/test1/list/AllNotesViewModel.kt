package com.example.test1.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test1.database.NoteData
import com.example.test1.database.NoteDatabase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class AllNotesViewModel(
    private val database: NoteDatabase
) : ViewModel() {
    val notes = MutableLiveData<List<NoteData>>()
    val openNote = MutableLiveData<Pair<List<NoteData>, Int>>()
    val openAbout = MutableLiveData<Unit>()

    fun loadAllNotes() {
        viewModelScope.launch {
            loadNotes().collect {
                notes.postValue(it)
            }
        }
    }

    fun btnAboutActivityClick() {
        openAbout.value = Unit
    }

    fun openNote(notes: List<NoteData>, currentPosition: Int) {
        openNote.postValue(notes to currentPosition)
    }

    fun createNote(): Job {
        return viewModelScope.launch {
            val rawNote = NoteData(0, "", "")

            val dao = database.noteDao()
            val newNote = rawNote.run {
                copy(id = dao.insert(rawNote))
            }

            val newNoteList = mutableListOf<NoteData>().apply {
                addAll(notes.value ?: listOf())
                add(newNote)
            }

            notes.postValue(newNoteList)
            openNote(newNoteList, newNoteList.size - 1)
        }
    }

    private fun loadNotes(): Flow<List<NoteData>> = database.noteDao().getAll()
}
