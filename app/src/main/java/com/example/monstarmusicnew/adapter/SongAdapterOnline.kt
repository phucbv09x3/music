package com.example.monstarmusicnew.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.monstarmusicnew.R
import com.example.monstarmusicnew.common.Util
import com.example.monstarmusicnew.customInterface.ISongClick
import com.example.monstarmusicnew.model.SongM
import com.example.monstarmusicnew.view.fragment.OfflineFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_music.view.*

class  SongAdapterOnline(var mList: MutableList<SongM>, val onClick: ISongClick) :
    RecyclerView.Adapter<SongAdapterOnline.MusicOnlineHolder>() {
    var positionM = MutableLiveData<Int>()
    var rowIndex=-1
    class MusicOnlineHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameMusic: TextView = itemView.findViewById(R.id.tv_nameMusic)
        val nameSinger: TextView = itemView.findViewById(R.id.tv_nameSinger)
        val img: ImageView = itemView.findViewById(R.id.image_of_music)
    }

    fun setListMusic(mutableList: MutableList<SongM>) {
        val callback = SongDiffCallback(mList, mutableList)
        val result = DiffUtil.calculateDiff(callback)
        mList = mutableList
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SongAdapterOnline.MusicOnlineHolder {

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

        if (music.linkImage.isEmpty()) {
            Picasso.get().load(R.drawable.hoanghon).into(holder.img)
        } else {
            Glide.with(holder.img).load(music.linkImage).error(R.drawable.musicnew).into(holder.img)
        }
        if (rowIndex==position){
            holder.itemView.imv_knowIsRunning.visibility=View.VISIBLE
        }else{
            holder.itemView.imv_knowIsRunning.visibility=View.GONE

        }
    }


    class SongDiffCallback(var oldList: MutableList<SongM>, var newList: MutableList<SongM>): DiffUtil.Callback(){
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int  = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].songName == newList[newItemPosition].songName
    }
}