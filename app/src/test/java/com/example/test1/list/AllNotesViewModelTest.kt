package com.example.test1.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.test1.database.NoteDao
import com.example.test1.database.NoteData
import com.example.test1.database.NoteDatabase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*


@ExperimentalCoroutinesApi
class AllNotesViewModelTest {

    @get:Rule
    val instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testLoadAllNotes() = runBlocking(Dispatchers.Unconfined) {
        val notes = listOf(NoteData(0, "", ""))
       /* val viewModel = AllNotesViewModel(createMockDatabase(
            object : MockNoteDao() {
                override fun getAll(): Flow<List<NoteData>> = flowOf(notes)
            }
        ))*/

        val viewModel = mockk<AllNotesViewModel>()
        every { viewModel.loadAllNotes()} returns listOf<NoteData>()
      //  viewModel.loadAllNotes()
        var subscriptionCalled = false
        viewModel.notes.observeForever {
            subscriptionCalled = true
            Assert.assertEquals(it, notes)
        }
        Assert.assertTrue(subscriptionCalled)
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
    fun openAbout() = runBlocking(Dispatchers.Unconfined) {
        val viewModel = AllNotesViewModel(createMockDatabase(object : MockNoteDao() {}))
        viewModel.btnAboutActivityClick()
        Assert.assertEquals(Unit, viewModel.openAbout.value)
    }

    @Test
    fun openNote() = runBlocking(Dispatchers.Unconfined) {

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