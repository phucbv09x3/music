package com.example.monstarmusicnew.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monstarmusicnew.R
import com.example.monstarmusicnew.adapter.SongAdapter
import com.example.monstarmusicnew.customInterface.ISongClick
import com.example.monstarmusicnew.model.SongM
import com.example.monstarmusicnew.view.activity.HomeActivity
import com.example.monstarmusicnew.viewmodel.MusicViewModel
import kotlinx.android.synthetic.main.content_activity.*
import kotlinx.android.synthetic.main.fragment_offline.*
import kotlinx.android.synthetic.main.fragment_offline.view.*
import kotlinx.android.synthetic.main.item_music.*

class OfflineFragment : Fragment(), ISongClick {
    private var mMusicViewModel: MusicViewModel? = null
    private lateinit var mAdapter: SongAdapter
    var mPosition = 0
    var mListPlay = mutableListOf<SongM>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_offline, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.rcy_listOffline.layoutManager = LinearLayoutManager(activity)
        mAdapter = SongAdapter(mutableListOf<SongM>(), this)
        view.rcy_listOffline.adapter = mAdapter
        mMusicViewModel = MusicViewModel()

    }

    override fun clickItemOnline(songM: SongM, position: Int) {
        (activity as HomeActivity).mMusicService?.playMusic(songM, position)
        (activity as HomeActivity).tv_nameSingerShow?.text = songM.artistName
        (activity as HomeActivity).tv_nameMusicShow?.text = songM.songName

    }
}