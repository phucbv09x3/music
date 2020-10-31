package com.example.monstarmusicnew.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.monstarmusicnew.R
import com.example.monstarmusicnew.common.Util
import com.example.monstarmusicnew.customInterface.ISongClick
import com.example.monstarmusicnew.model.SongM
import com.example.monstarmusicnew.service.MusicService
import com.example.monstarmusicnew.view.activity.HomeActivity
import com.example.monstarmusicnew.view.fragment.OfflineFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_music.*
import kotlinx.android.synthetic.main.item_music.view.*

class SongAdapter(var mList: MutableList<SongM>, val onClick: ISongClick) :
    RecyclerView.Adapter<SongAdapter.MusicOnlineHolder>() {
    var positionM = MutableLiveData<Int>()
    var rowIndex=-1
    class MusicOnlineHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameMusic = itemView.findViewById<TextView>(R.id.tv_nameMusic)
        val nameSinger = itemView.findViewById<TextView>(R.id.tv_nameSinger)
        val img = itemView.findViewById<ImageView>(R.id.image_of_music)
    }

    fun setListMusic(mutableList: MutableList<SongM>) {
        this.mList = mutableList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SongAdapter.MusicOnlineHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_music, parent, false)
        return MusicOnlineHolder(v)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MusicOnlineHolder, position: Int) {
        val music = mList[position]
        holder.nameMusic.text = music.songName
        holder.nameSinger.text = music.artistName
        positionM.value = position
        holder.itemView.setOnClickListener {
            onClick?.clickItemOnline(mList[position], holder.adapterPosition)
            rowIndex=position
            notifyDataSetChanged()
        }
        if (Util.songArt(mList[position].linkMusic) == null) {
            holder.img.setImageResource(R.drawable.hoanghon)
        } else {
            holder.img.setImageBitmap(Util.songArt(mList[position].linkMusic))
        }
        if (rowIndex==position){
            holder.itemView.imv_knowIsRunning.visibility=View.VISIBLE
        }else{
            holder.itemView.imv_knowIsRunning.visibility=View.GONE

        }

    }

}


