package com.example.monstarmusicnew.viewmodel

import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.monstarmusicnew.customInterface.SongRepository
import com.example.monstarmusicnew.model.Lyric
import com.example.monstarmusicnew.model.SongLinkOnline
import com.example.monstarmusicnew.model.SongM
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MusicViewModel : ViewModel() {

    private var mListMusicInViewModel = mutableListOf<SongM>()
    var listMusicOffline = MutableLiveData<MutableList<SongM>>()

    var listMusicOnline = MutableLiveData<MutableList<SongM>>()
    var linkGetOnline = MutableLiveData<SongLinkOnline>()
    var lyricMusic = MutableLiveData<Lyric>()

    companion object {
        const val baseUrl = "http://192.168.86.2:5000"
    }

    private val songRepository: SongRepository = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(
            RxJava2CallAdapterFactory.create()
        ).build()
        .create(SongRepository::class.java)

    fun searchSong(name: String) {
        val call = songRepository?.searchSong(name)
        call?.enqueue(object : Callback<MutableList<SongM>> {
            override fun onResponse(
                call: Call<MutableList<SongM>>,
                response: Response<MutableList<SongM>>
            ) {
                listMusicOnline.value = response.body()
            }

            override fun onFailure(call: Call<MutableList<SongM>>, t: Throwable) {
                Log.d("fail", t.toString())
            }

        })
    }

    fun getFullLinkOnline(link: String) {
        val call = songRepository?.getLinkMusic(link)
        call?.enqueue(object : Callback<SongLinkOnline> {
            override fun onResponse(
                call: Call<SongLinkOnline>,
                response: Response<SongLinkOnline>
            ) {
                linkGetOnline.value = response.body()
            }

            override fun onFailure(call: Call<SongLinkOnline>, t: Throwable) {
            }
        })
    }

    fun getLyricOfMusic(link: String) {
        val call = songRepository.getLyricMusic(link)
        call.enqueue(object : Callback<Lyric> {
            override fun onResponse(call: Call<Lyric>, response: Response<Lyric>) {
                lyricMusic.value = response.body()
            }

            override fun onFailure(call: Call<Lyric>, t: Throwable) {

            }

        })
    }

    fun getListMusicOffLine(contentResolver: ContentResolver) {
        val uriri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val cursor = contentResolver.query(uriri, null, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            val urii = cursor.getColumnIndex(MediaStore.Audio.Media.DATA)
            val id = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
            val title = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val songArtist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            do {
                val idd = cursor.getString(id)
                val currentTT = cursor.getString(title)
                val currentArtist = cursor.getString(songArtist)
                val uri = cursor.getString(urii)
                mListMusicInViewModel.add(
                    SongM(
                        idd,
                        "",
                        currentTT,
                        currentArtist,
                        "",
                        uri
                    )
                )
            } while (cursor.moveToNext())
            listMusicOffline.value = mListMusicInViewModel
        }
    }
}