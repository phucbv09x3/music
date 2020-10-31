package com.example.monstarmusicnew.customInterface

import com.example.monstarmusicnew.model.Lyric
import com.example.monstarmusicnew.model.SongLinkOnline
import com.example.monstarmusicnew.model.SongM
import retrofit2.http.GET
import retrofit2.http.Query

interface SongRepository {
    @GET("/api/searchSong")
    fun searchSong(
        @Query(value = "songName")
        name:String
    ) : retrofit2.Call<MutableList<SongM>>

    @GET("/api/linkMusic")
    fun getLinkMusic(
        @Query(value = "linkSong")
        linkSong:String
    ) : retrofit2.Call<SongLinkOnline>

    @GET("/api/getLyric")
    fun getLyricMusic(@Query(value = "linkSong")
    linkSong: String):retrofit2.Call<Lyric>
}