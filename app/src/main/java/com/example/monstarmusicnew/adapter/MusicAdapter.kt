//package com.example.monstarmusicnew.adapter
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.lifecycle.MutableLiveData
//import androidx.recyclerview.widget.RecyclerView
//import com.example.monstarmusicnew.R
//import com.example.monstarmusicnew.common.Util
//
//class MusicAdapter( val onClick:IClickItemInList)
//    : RecyclerView.Adapter<MusicAdapter.MusicHolder>() {
//    var positionM=MutableLiveData<Int>()
//
//    private var mList: MutableList<SongOffline> = mutableListOf()
//    class MusicHolder(itemView:View):RecyclerView.ViewHolder(itemView){
//        val nameMusic=itemView.findViewById<TextView>(R.id.tv_nameMusic)
//        val nameSinger=itemView.findViewById<TextView>(R.id.tv_nameSinger)
//        val img=itemView.findViewById<ImageView>(R.id.image_of_music)
//    }
//    fun setListMusic( mutableList: MutableList<SongOffline>){
//        this.mList=mutableList
//        notifyDataSetChanged()
//    }
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicAdapter.MusicHolder {
//
//        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_music, parent, false)
//        return MusicHolder(v)
//    }
//    override fun getItemCount(): Int {
//        return mList.size
//    }
//    override fun onBindViewHolder(holder: MusicAdapter.MusicHolder, position: Int) {
//        val music=mList[position]
//        holder.nameMusic.text=music.nameMusic
//        holder.nameSinger.text=music.nameSinger
//        positionM.value=position
//       holder.itemView.setOnClickListener {
//           onClick?.clickOnItem(mList[position],holder.adapterPosition)
//       }
//        if(Util.songArt(mList[position].uri)==null){
//            holder.img.setImageResource(R.drawable.hoanghon)
//        }else{
//            holder.img.setImageBitmap(Util.songArt(mList[position].uri))
//        }
//    }
//
//
//}
//
//
