package com.example.test1.pager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.test1.R
import com.example.test1.database.NoteData
import com.example.test1.note.NoteFragment

class NotePagerActivity : FragmentActivity(), PagerView {

    private lateinit var presenter: PagerPresenter
    private lateinit var viewPager: ViewPager2
    private lateinit var toolbar: Toolbar
    private lateinit var adapter: PagerListAdapter
    var currentFragment: NoteFragment?= null

    private val startPosition: Int by lazy {
        intent.getIntExtra(SELECTED_POSITION, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager)
        toolbar = findViewById(R.id.toolbar)
        //AppCompatActivity().setSupportActionBar(toolbar)
        viewPager = findViewById<ViewPager2>(R.id.viewpager).apply {
            val list = intent.getParcelableArrayListExtra(NOTES_LIST) ?: emptyList<NoteData>()
            this@NotePagerActivity.adapter = PagerListAdapter(list, this@NotePagerActivity)
            adapter = this@NotePagerActivity.adapter
            setCurrentItem(startPosition, false)

        }
        presenter = PagerPresenter(this)
        findViewById<ImageButton>(R.id.buttonSave).also {
            it.setOnClickListener {
               //  presenter.saveNote()
                currentFragment?.save()
            }
        }
    }

    companion object {
        private const val SELECTED_POSITION = "selectedPosition"
        private const val NOTES_LIST = "notesList"

        fun startActivity(
            context: Context,
            notesList: List<NoteData>,
            selectionPosition: Int
        ) {
            val intent = Intent(context, NotePagerActivity::class.java)
            intent.putParcelableArrayListExtra(NOTES_LIST, ArrayList(notesList))
            intent.putExtra(SELECTED_POSITION, selectionPosition)

            context.startActivity(intent)
        }
    }

    override fun onSaveSuccess() {
        showNotification(R.string.save_msg)
    }

    override fun onNoteEmpty() {
        showNotification(R.string.msg_error)
    }

    private fun showNotification(msg_toast: Int) {
        Toast.makeText(this, msg_toast, Toast.LENGTH_SHORT)
            .show()
    }
}