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
    fun testLoadAllNotesMoreTwoNotes() = runBlocking(Dispatchers.Unconfined) {
        val notes = listOf(noteDataOne, noteDataTwo)
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
    fun testLoadAllNotesOneNote() = runBlocking(Dispatchers.Unconfined) {
        val notes = listOf(noteDataOne)
        val database: NoteDatabase = mockk()
        val noteDao: NoteDao = mockk()
        val viewModel = AllNotesViewModel(database)
        every { database.noteDao() } returns noteDao
        every { noteDao.getAll() } returns flowOf(notes)

        viewModel.loadAllNotes()
        var subscriptionOneNote = false
        viewModel.notes.observeForever {
            subscriptionOneNote = true
            Assert.assertEquals(it, notes)
        }
        Assert.assertTrue(subscriptionOneNote)
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
        var subscriptionNoNotes = false
        viewModel.notes.observeForever {
            subscriptionNoNotes = true
            Assert.assertEquals(it, notes)
        }
        Assert.assertTrue(subscriptionNoNotes)
    }

    @Test
    fun testCreateOneNote() = runBlocking {
        val database: NoteDatabase = mockk()
        val noteDao: NoteDao = mockk()
        val noteId: Long = 2
        val newNote = NoteData(0, "", "")
        val newNote2 = newNote.copy(id = noteId)
        every { database.noteDao() } returns noteDao
        coEvery { noteDao.insert(newNote) } returns noteId
        val viewModel = AllNotesViewModel(database)

        viewModel.createNote()
        var subscriptionOneNote = false
        viewModel.notes.observeForever {
            subscriptionOneNote = true
            Assert.assertEquals(it, listOf(newNote2))
        }
        Assert.assertTrue(subscriptionOneNote)

        var subscriptionOpenNote = false
        viewModel.openNote.observeForever {
            subscriptionOpenNote = true
            Assert.assertEquals(it, listOf(newNote2) to 0)
        }
        Assert.assertTrue(subscriptionOpenNote)
    }

    @Test
    fun testCreateMoreTwoNotes() = runBlocking {
        val notes = listOf(noteDataOne)
        val database: NoteDatabase = mockk()
        val noteDao: NoteDao = mockk()
        val newNote = NoteData(0, "", "")
        val newNote2 = newNote.copy(id = 2)
        val testNotes = listOf(noteDataOne, newNote2)
        every { database.noteDao() } returns noteDao
        coEvery { noteDao.insert(newNote) } returns 2
        every { noteDao.getAll() } returns flowOf(notes)

        val viewModel = AllNotesViewModel(database)
        viewModel.loadAllNotes()
        viewModel.createNote()

        var subscriptionTwoNotes = false
        viewModel.notes.observeForever {
            subscriptionTwoNotes = true
            Assert.assertEquals(it, testNotes)//вынести
        }
        Assert.assertTrue(subscriptionTwoNotes)

        var subscriptionOpenNote = false
        viewModel.openNote.observeForever {
            subscriptionOpenNote = true
            Assert.assertEquals(it, testNotes to 1)
        }
        Assert.assertTrue(subscriptionOpenNote)
    }

    @Test
    fun testOpenAbout() = runBlocking(Dispatchers.Unconfined) {
        val database: NoteDatabase = mockk()
        val viewModel = AllNotesViewModel(database)
        viewModel.btnAboutActivityClick()
        var subscriptionOpenAbout = false
        viewModel.openAbout.observeForever {
            subscriptionOpenAbout = true
        }
        Assert.assertTrue(subscriptionOpenAbout)
    }

    @Test
    fun testOpenOneNote() = runBlocking(Dispatchers.Unconfined) {
        val currentPosition = 0
        val notes: List<NoteData> = listOf(noteDataOne)
        val database: NoteDatabase = mockk()
        val viewModel = AllNotesViewModel(database)
        viewModel.openNote(notes, currentPosition)
        var subscriptionOneNotes = false
        viewModel.openNote.observeForever {
            subscriptionOneNotes = true
            Assert.assertEquals(it, notes to currentPosition)
        }
        Assert.assertTrue(subscriptionOneNotes)
    }

    @Test
    fun testOpenMoreTwoNotes() = runBlocking(Dispatchers.Unconfined) {
        val notes: List<NoteData> =
            listOf(noteDataZero, noteDataTwo, noteDataThree)
        val currentPosition = 1
        val database: NoteDatabase = mockk()
        val viewModel = AllNotesViewModel(database)
        viewModel.openNote(notes, currentPosition)
        var subscriptionMoteTwoNotes = false
        viewModel.openNote.observeForever {
            subscriptionMoteTwoNotes = true
            Assert.assertEquals(it, notes to currentPosition)
        }
        Assert.assertTrue(subscriptionMoteTwoNotes)
    }

    companion object {
        val noteDataZero = NoteData(0, "title0", "text0")
        val noteDataOne = NoteData(1, "title1", "text1")
        val noteDataTwo = NoteData(2, "title2", "text2")
        val noteDataThree = NoteData(3, "title3", "text3")
    }
}
