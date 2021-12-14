package com.example.test1

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.test1.database.NoteData

class PagerListAdapter(
    private val notes: List<NoteData>,
    fragment: FragmentActivity
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = notes.size

    override fun createFragment(position: Int): Fragment =
        NoteFragment.newInstance(notes[position])
}