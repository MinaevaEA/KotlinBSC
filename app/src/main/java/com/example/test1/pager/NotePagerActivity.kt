package com.example.test1.pager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.test1.database.NoteData
import com.example.test1.databinding.ActivityViewpagerBinding
import com.example.test1.note.DialogNoteFragment
import com.example.test1.note.NoteFragment

class NotePagerActivity : FragmentActivity() {
    var currentFragment: NoteFragment? = null

    private lateinit var binding: ActivityViewpagerBinding
    private lateinit var adapter: PagerListAdapter
    private val startPosition by lazy { intent.getIntExtra(SELECTED_POSITION, 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewpagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewpager.apply {
            val list = intent.getParcelableArrayListExtra(NOTES_LIST) ?: emptyList<NoteData>()
            this@NotePagerActivity.adapter = PagerListAdapter(list, this@NotePagerActivity)
            adapter = this@NotePagerActivity.adapter
            setCurrentItem(startPosition, false)
        }
        binding.buttonSave.also {
            it.setOnClickListener {
                val openDialog = DialogNoteFragment()
                val fragmentManager = this@NotePagerActivity.supportFragmentManager
                openDialog.show(fragmentManager, "name")
            }
        }
    }

    fun continueSave() {
        currentFragment?.save()
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
            intent.putExtra(NOTES_LIST, ArrayList(notesList))
            intent.putExtra(SELECTED_POSITION, selectionPosition)

            context.startActivity(intent)
        }
    }
}