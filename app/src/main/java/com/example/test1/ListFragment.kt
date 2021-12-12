package com.example.test1


import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteData(var title: String, var text: String) : Parcelable

class ListFragment : Fragment(), ListMainView {

    private lateinit var presenter: ListPresenter
    private lateinit var adapter: ListAdapter
    private lateinit var btnAboutClick: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_list, container, false) //recycler ok

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ListPresenter(this)
        val recyclerFragment: RecyclerView =
            view.findViewById(R.id.recyclerView) as RecyclerView //ok
        recyclerFragment.layoutManager = LinearLayoutManager(context)
        recyclerFragment.adapter = adapter
        btnAboutClick = view.findViewById(R.id.about)

        btnAboutClick.setOnClickListener {
            presenter.btnAboutActivityClick()
        }
    }

    override fun openActivityAbout() {
        startActivity(Intent(requireContext(), AboutActivity::class.java))
    }

    override fun showNoteList(notes: List<NoteData>) {
        adapter = ListAdapter(notes, ::openNote)
    }

    override fun onNoteOpen(notes: List<NoteData>, currentPosition: Int) {
        context?.let {
            NotePagerActivity.startActivity(it, notes, currentPosition)
        }
    }

    private fun openNote(notes: List<NoteData>, currentPosition: Int) {
        presenter.openNote(notes, currentPosition)
    }
}
