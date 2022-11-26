package com.example.milestone2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MemeViewAdapter(private val memeList: List<MemeModel>):RecyclerView.Adapter<MemeViewAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.meme_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model:MemeModel = memeList[position]
        holder.memeName.text = model.memeName!!
        Picasso.get().load(model.memeImageSrc).into(holder.memeImage)
    }

    override fun getItemCount(): Int {
        return memeList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView)
    {
        var memeName: TextView = itemView.findViewById(R.id.meme_name)
        var memeImage: ImageView= itemView.findViewById(R.id.meme_src_id)

    }


}