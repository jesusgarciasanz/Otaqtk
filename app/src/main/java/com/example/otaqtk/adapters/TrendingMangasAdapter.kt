package com.example.otaqtk.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.otaqtk.R
import com.example.otaqtk.kitsu_pojo.Data
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso

class TrendingMangasAdapter(val newMangas: MutableList<Data>, var listener: ClickListener) :
    RecyclerView.Adapter<TrendingMangasAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.rated_card_home, parent, false)
        return ViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return newMangas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val homeMangas = newMangas.get(position)
        Picasso.get().load(homeMangas.attributes.posterImage.medium).fit().centerCrop()
            .into(holder.image)

    }

    class ViewHolder(itemView: View, listener: ClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var image : ImageView? = null
        var listener: ClickListener? = null

        init {
            image = itemView.findViewById(R.id.rated_card_image_home)
            this.listener = listener
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            this.listener?.onClick(v!!, adapterPosition)
        }

    }
}