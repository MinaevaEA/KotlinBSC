package com.example.test1


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView


class FragmentAdapter(private var notes: List<NoteData>) :
    RecyclerView.Adapter<FragmentAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val title: TextView = itemView.findViewById(R.id.title)
        private val content: TextView = itemView.findViewById(R.id.text)

        fun bind(data: NoteData) {
            title.text = data.title
            content.text = data.subtitle
            itemView.setOnClickListener { v ->
                val activity = v.context as AppCompatActivity
                val newFragment = NoteFragment.newInstance(data.title, data.subtitle)
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, newFragment).commit()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_item, parent, false) //ok
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount(): Int = notes.size
}


