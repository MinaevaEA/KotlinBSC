package com.example.test1.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.test1.database.NoteDao
import com.example.test1.database.NoteData
import com.example.test1.database.NoteDatabase
import io.mockk.coEvery
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
import java.lang.Exception


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
    fun testLoadAllNotesMore2Notes() = runBlocking(Dispatchers.Unconfined) {
        val notes = listOf(NoteData(1, "gfbg", "fbfb"), NoteData(2, "nhnh", "jmjhmh"))
        val database: NoteDatabase = mockk()
        val noteDao: NoteDao = mockk()
        val viewModel = AllNotesViewModel(database)
        every { database.noteDao() } returns noteDao
        every { noteDao.getAll() } returns flowOf(notes)

        viewModel.loadAllNotes()
        var subscriptionCalled = false
        viewModel.notes.observeForever {
            subscriptionCalled = true
            Assert.assertEquals(it, notes)
        }
        Assert.assertTrue(subscriptionCalled)
    }

    @Test
    fun testLoadAllNotes1Note() = runBlocking(Dispatchers.Unconfined) {
        val notes = listOf(NoteData(1, "gfbg", "fbfb"))
        val database: NoteDatabase = mockk()
        val noteDao: NoteDao = mockk()
        val viewModel = AllNotesViewModel(database)
        every { database.noteDao() } returns noteDao
        every { noteDao.getAll() } returns flowOf(notes)

        viewModel.loadAllNotes()
        var subscriptionCalled = false
        viewModel.notes.observeForever {
            subscriptionCalled = true
            Assert.assertEquals(it, notes)
        }
        Assert.assertTrue(subscriptionCalled)
    }

    @Test
    fun testLoadAllNotesNoNotes() = runBlocking(Dispatchers.Unconfined) {
        val notes = listOf<NoteData>()
        val database: NoteDatabase = mockk()
        val noteDao: NoteDao = mockk()
        val viewModel = AllNotesViewModel(database)
        every { database.noteDao() } returns noteDao
        every { noteDao.getAll() } returns flowOf(notes)

        viewModel.loadAllNotes()
        var subscriptionCalled = false
        viewModel.notes.observeForever {
            subscriptionCalled = true
            Assert.assertEquals(it, notes)
        }
        Assert.assertTrue(subscriptionCalled)
    }

    @Test
    fun testCreateNote() = runBlocking {
        val database: NoteDatabase = mockk()
        val noteDao: NoteDao = mockk()
        val newNote = NoteData(0, "", "")
        val newNote2 = newNote.copy(id = 2)
        every { database.noteDao() } returns noteDao
        coEvery { noteDao.insert(newNote) } returns 2
        val viewModel = AllNotesViewModel(database)

        viewModel.createNote()
        var subscriptionCalled = false
        viewModel.notes.observeForever {
            subscriptionCalled = true
            Assert.assertEquals(it, listOf(newNote2))
        }
        Assert.assertTrue(subscriptionCalled)

        var f = false
        viewModel.openNote.observeForever {
            f = true
            Assert.assertEquals(it, listOf(newNote2) to 0)
        }
        Assert.assertTrue(f)
    }

    @Test
    fun testCreateNote1() = runBlocking {
        val notes = listOf(NoteData(1, "gfbg", "fbfb"))
        val database: NoteDatabase = mockk()
        val noteDao: NoteDao = mockk()
        val newNote = NoteData(0, "", "")
        val newNote2 = newNote.copy(id = 2)
        every { database.noteDao() } returns noteDao
        coEvery { noteDao.insert(newNote) } returns 2
        every { noteDao.getAll() } returns flowOf(notes)
        val viewModel = AllNotesViewModel(database)
        viewModel.loadAllNotes()
        viewModel.createNote()
        var subscriptionCalled = false
        viewModel.notes.observeForever {
            subscriptionCalled = true
            Assert.assertEquals(it, listOf(NoteData(1, "gfbg", "fbfb"), newNote2))//вынести
        }
        Assert.assertTrue(subscriptionCalled)

        var f = false
        viewModel.openNote.observeForever {
            f = true
            Assert.assertEquals(it, listOf(NoteData(1, "gfbg", "fbfb"), newNote2) to 1)
        }
        Assert.assertTrue(f)
    }

    @Test
    fun openAbout() = runBlocking(Dispatchers.Unconfined) {
        val database: NoteDatabase = mockk()
        val viewModel = AllNotesViewModel(database)
        viewModel.btnAboutActivityClick()
        var s = false
        viewModel.openAbout.observeForever {
            s = true
        }
        Assert.assertTrue(s)
    }

    @Test
    fun openNote() = runBlocking(Dispatchers.Unconfined) {

        val currentPosition = 0
        val notes: List<NoteData> = listOf(NoteData(0, "", ""))
        val database: NoteDatabase = mockk()
        val viewModel = AllNotesViewModel(database)
        viewModel.openNote(notes, currentPosition)
        var s = false
        viewModel.openNote.observeForever {
            s = true
            Assert.assertEquals(it, notes to 0)
        }
        Assert.assertTrue(s)
    }

    @Test
    fun openNote2() = runBlocking(Dispatchers.Unconfined) {

        val currentPosition = 0
        val notes: List<NoteData> = listOf(NoteData(0, "", ""),NoteData(2, "", ""),NoteData(3, "", ""))
        val database: NoteDatabase = mockk()
        val viewModel = AllNotesViewModel(database)
        viewModel.openNote(notes, 1)
        var s = false
        viewModel.openNote.observeForever {
            s = true
            Assert.assertEquals(it, notes to 1)
        }
        Assert.assertTrue(s)
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