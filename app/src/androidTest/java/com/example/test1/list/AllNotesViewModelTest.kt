package com.example.test1.list

import com.example.test1.database.NoteDao
import com.example.test1.database.NoteData
import com.example.test1.database.NoteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class AllNotesViewModelTest {

    @Test
    fun loadAllNotes() = runBlocking {
        val notes = listOf(NoteData(0, "", ""))
        val viewModel = AllNotesViewModel(createMockDatabase(
            object : MockNoteDao() {
                override fun getAll(): Flow<List<NoteData>> = flowOf(notes)
            }
        ))

        viewModel.loadAllNotes().join()

        Assert.assertEquals(viewModel.notes.value, notes)
    }

    @Test
    fun createNote() = runBlocking {
        val noteId: Long = 999
        val newNote = NoteData(noteId, "", "")

        val noteDao = object : MockNoteDao() {
            var savedNote: NoteData? = null
            override suspend fun insert(note: NoteData): Long {
                savedNote = note.copy(id = noteId)
                return noteId
            }
        }
        val viewModel = AllNotesViewModel(createMockDatabase(noteDao))

        viewModel.createNote().join()

        Assert.assertEquals(newNote, noteDao.savedNote)
        Assert.assertEquals(listOf(newNote), viewModel.notes.value)
        Assert.assertEquals(listOf(newNote) to 0, viewModel.openNote.value)
    }

    @Test
    fun openAbout() = runBlocking(Dispatchers.Main) {
        val viewModel = AllNotesViewModel(createMockDatabase(object : MockNoteDao() {}))
        viewModel.btnAboutActivityClick()
        Assert.assertEquals(Unit, viewModel.openAbout.value)
    }

    @Test
    fun openNote() = runBlocking(Dispatchers.Main) {
        val currentPosition = 0
        val notes: List<NoteData> = listOf(NoteData(0, "", ""))
        val viewModel = AllNotesViewModel(createMockDatabase(object : MockNoteDao() {}))

        viewModel.openNote(notes, currentPosition)

        Assert.assertEquals(notes to currentPosition, viewModel.openNote.value)
    }
}

private fun createMockDatabase(noteDao: NoteDao): NoteDatabase =
    object : NoteDatabase {
        override fun noteDao(): NoteDao = noteDao
    }

private abstract class MockNoteDao : NoteDao {
    override fun getAll(): Flow<List<NoteData>> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(note: NoteData): Long {
        TODO("Not yet implemented")
    }

    override suspend fun update(note: NoteData) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(note: NoteData) {
        TODO("Not yet implemented")
    }
}