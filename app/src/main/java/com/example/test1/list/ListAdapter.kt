package com.example.test1.list


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.test1.R
import com.example.test1.database.NoteData

class ListAdapter(
    private val notes: List<NoteData>,
    private val clickListener: (List<NoteData>, Int) -> Unit
) : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val title: TextView = itemView.findViewById(R.id.title)
        private val content: TextView = itemView.findViewById(R.id.text)

        fun bind(data: NoteData, clickListener: View.OnClickListener) {
            title.text = data.title
            content.text = data.text
            itemView.setOnClickListener(clickListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_item, parent, false)
        )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(notes[position]) { clickListener(notes, position) }
    }

    override fun getItemCount(): Int = notes.size
}


