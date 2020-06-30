package com.example.otaqtk.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.otaqtk.R
import com.example.otaqtk.kitsu_pojo.Data
import com.squareup.picasso.Picasso

class CategoryAdapter (val categories: MutableList<Data>, var listener: ClickListener): RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return ViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val homeCategories = categories.get(position)
        holder.category?.text = homeCategories.attributes.title
    }

    class ViewHolder(itemView: View, listener: ClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var image : ImageView? = null
        var category: TextView? = null
        var listener : ClickListener? = null

        init {
            image = itemView.findViewById(R.id.category_card_image)
            category = itemView.findViewById(R.id.category_card_title)
            this.listener = listener
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            this.listener?.onClick(v!!, adapterPosition)
        }

    }
}