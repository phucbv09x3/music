package com.example.monstarmusicnew.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.monstarmusicnew.R
import com.example.monstarmusicnew.viewmodel.MusicViewModel
import kotlinx.android.synthetic.main.activity_lyric.*

class LyricActivity : AppCompatActivity() {
    private var musicViewModel:MusicViewModel?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        musicViewModel= MusicViewModel()
        setContentView(R.layout.activity_lyric)
        val linkGetFromMusicIsRunning=intent?.getStringExtra("keyLink")
        linkGetFromMusicIsRunning?.let {
            musicViewModel?.getLyricOfMusic(linkGetFromMusicIsRunning!!)
            musicViewModel?.lyricMusic?.observe(this, Observer {itLyric->
                if (itLyric.lyric==null){
                    Toast.makeText(this,"Khong co lời bài hát",Toast.LENGTH_LONG).show()
                }else{
                    tv_lyric.text=itLyric.lyric.toString()
                }

            })
        }


    }
}