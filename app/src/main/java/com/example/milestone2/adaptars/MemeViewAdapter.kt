package com.example.milestone2.adaptars

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.milestone2.R
import com.example.milestone2.memeclasses.Meme
import com.squareup.picasso.Picasso

class MemeViewAdapter(private val memeList: List<Meme>):RecyclerView.Adapter<MemeViewAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.meme_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model:Meme = memeList[position]
        holder.memeName.text = model.name!!
        // Picasso to load image from link
        // in api response the image src is in the form of link
        Picasso.get().load(model.url).into(holder.memeImage)
    }

    override fun getItemCount(): Int {
        return memeList.size
    }

    // view holder class that extends the Recycler view holder
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView)
    {
        var memeName: TextView = itemView.findViewById(R.id.meme_name)
        var memeImage: ImageView= itemView.findViewById(R.id.meme_src_id)

    }
}