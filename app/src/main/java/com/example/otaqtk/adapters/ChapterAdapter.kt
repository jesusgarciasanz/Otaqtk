package com.example.otaqtk.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.otaqtk.R
import com.example.otaqtk.api.Config
import com.example.otaqtk.kitsu_pojo.Data
import com.example.otaqtk.kitsu_pojo.DataClass
import com.example.otaqtk.kitsu_pojo.PosterImage
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso

class ChapterAdapter(val chapters: MutableList<Data> , var listener: ClickListener, var posterImage: String): RecyclerView.Adapter<ChapterAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rated_card_item, parent, false)
        return ViewHolder(view, listener, posterImage)
    }

    override fun getItemCount(): Int {
        return chapters.size
    }

    override fun onBindViewHolder(holder: ChapterAdapter.ViewHolder, position: Int) {
        val chapterlist = chapters.get(position)
        if(chapterlist.attributes.thumbnail?.original != null){
            Picasso.get().load(chapterlist.attributes.thumbnail.original).fit().centerCrop().into(holder.image)
        }else if(posterImage != ""){
            Picasso.get().load(posterImage).fit().centerCrop().into(holder.image)
        }
            holder.text?.text = chapterlist.attributes.number.toString()
    }

    class ViewHolder(itemView: View, listener: ClickListener, posterImage: String) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var image: ShapeableImageView? =null
        var text: TextView? = null
        var listener: ClickListener? = null
        var posterImage: String? = null
        init {
            image = itemView.findViewById(R.id.rated_card_image)
            text = itemView.findViewById(R.id.text_rating_card)
            this.posterImage = posterImage
            this.listener = listener
            itemView.setOnClickListener(this)

        }

        override fun onClick(v: View?) {
            this.listener?.onClick(v!!, adapterPosition)
        }

    }
}