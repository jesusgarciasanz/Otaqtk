package com.example.otaqtk.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.otaqtk.R
import com.example.otaqtk.pojo.Manga
import com.example.otaqtk.pojo.RatedMangas
import com.google.android.material.imageview.ShapeableImageView

class RatedMangaAdapter(val mangas: ArrayList<RatedMangas>) : RecyclerView.Adapter<RatedMangaAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rated_card_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mangas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ratedMangas = mangas.get(position)
        holder.image.setImageResource(R.drawable.kimetsutest)
        holder.rating.text = ratedMangas.valoration
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image = itemView.findViewById(R.id.rated_card_image) as ShapeableImageView
        val rating = itemView.findViewById(R.id.text_rating_card) as TextView
    }
}