package com.example.monstarmusicnew.customInterface

import com.example.monstarmusicnew.model.SongM

interface ISongClick {
    fun clickItemOnline(songM: SongM, position:Int)
}