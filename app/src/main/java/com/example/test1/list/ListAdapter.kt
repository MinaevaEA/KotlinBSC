package com.example.test1.list


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test1.database.NoteData
import com.example.test1.databinding.ListItemBinding

class ListAdapter(
    private val notes: List<NoteData>,
    private val clickListener: (List<NoteData>, Int) -> Unit
) : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    class MyViewHolder(private var binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: NoteData, clickListener: View.OnClickListener) {
            binding.title.text = data.title
            binding.text.text = data.text
            binding.noteContainer.setOnClickListener(clickListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListItemBinding.inflate(
            LayoutInflater
                .from(parent.context), parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(notes[position]) { clickListener(notes, position) }
    }

    override fun getItemCount(): Int = notes.size
}
