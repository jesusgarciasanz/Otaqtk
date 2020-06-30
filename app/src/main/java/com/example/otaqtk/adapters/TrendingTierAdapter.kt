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

class TrendingTierAdapter(val trendingList: MutableList<Data>, var listener: ClickListener): RecyclerView.Adapter<TrendingTierAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingTierAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trending_card, parent, false)
        return ViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return trendingList.size
    }

    override fun onBindViewHolder(holder: TrendingTierAdapter.ViewHolder, position: Int) {
        val trendingContent = trendingList.get(position)

        if (trendingContent.attributes.titles?.en != null){
            holder.title?.text = trendingContent.attributes.titles.en
        }else{
            holder.title?.text = trendingContent.attributes.titles?.enJp
        }
        holder.ranking?.text = trendingContent.attributes.ratingRank.toString()
        Picasso.get().load(trendingContent.attributes.posterImage?.medium).fit().centerCrop().into(holder.image)

    }
    class ViewHolder(itemView: View, listener: ClickListener): RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var image: ImageView? = null
        var title : TextView? = null
        var ranking : TextView? = null
        var listener: ClickListener? = null

        init{
            image = itemView.findViewById(R.id.trending_card_image)
            title = itemView.findViewById(R.id.trending_title_card)
            ranking = itemView.findViewById(R.id.trending_card_rating)
            this.listener = listener
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.onClick(v!!, adapterPosition)
        }

    }


}