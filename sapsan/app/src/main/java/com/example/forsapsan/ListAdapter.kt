package com.example.forsapsan

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(private val list: List<Rate>) : RecyclerView.Adapter<MyViewHolder>() {
    private lateinit var mListener: OnItemClickListener


    interface OnItemClickListener {
        fun onItemClick(position: Int) {

        }
    }

    fun setOnClickListener(listener: OnItemClickListener) {
        mListener = listener

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(inflater, parent, mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val rate: Rate = list[position]
        holder.bind(rate)
    }

    override fun getItemCount(): Int = list.size
}

class MyViewHolder(
    inflater: LayoutInflater,
    parent: ViewGroup,
    listener: ListAdapter.OnItemClickListener
) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item, parent, false)) {
    private var titleView: TextView? = itemView.findViewById(R.id.list_title)
    private var priceView: TextView? = itemView.findViewById(R.id.list_price)
    private var contentView: TextView? = itemView.findViewById(R.id.list_content)

    fun bind(rate: Rate) {

        titleView?.text = rate.title
        priceView?.text = rate.price
        contentView?.text = rate.content
    }

    init {
        itemView.setOnClickListener {

            listener.onItemClick(bindingAdapterPosition)
            itemView.setBackgroundColor(Color.BLUE)
          //  titleView?.typeface = Typeface.DEFAULT_BOLD
        }

    }
}