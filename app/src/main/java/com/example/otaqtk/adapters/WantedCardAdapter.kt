package com.example.otaqtk.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.otaqtk.R
import com.example.otaqtk.pojo.Manga
import com.google.android.material.imageview.ShapeableImageView

class WantedCardAdapter(val mangas: ArrayList<Manga>) : RecyclerView.Adapter<WantedCardAdapter.ViewHolder>() {


    //TODO CHANGE ONBINDVIEWHOLDER
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.unrated_manga_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mangas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val manga: Manga = mangas.get(position)
        holder.itemImage.setImageResource(manga.image)
        holder.itemName.text = manga.title
        holder.valoration.text =manga.valoration
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage = itemView.findViewById(R.id.unratedcard_image) as ShapeableImageView
        val itemName= itemView.findViewById(R.id.card_title) as TextView
        val valoration = itemView.findViewById(R.id.card_valoration) as TextView
    }
}