package com.example.monstarmusicnew.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import com.example.monstarmusicnew.R
import com.example.monstarmusicnew.model.SongM
import com.example.monstarmusicnew.view.activity.HomeActivity
import com.example.monstarmusicnew.viewmodel.MusicViewModel

class MusicService : Service() {
    var mutableL = MutableLiveData<SongM>()
    var musicFromService = MutableLiveData<SongM>()
    var currentPos = MutableLiveData<Int>()

    companion object {
        const val ACTION_PLAY = "play"
        const val ACTION_PREVIOUS = "previous"
        const val ACTION_NEXT = "next"
        const val ACTION_CLOSE = "close"
    }


    private lateinit var mMusicViewModel: MusicViewModel
    private var mMusicManager: MusicManager? = null
    fun getMusicManager() = mMusicManager
    fun getModel() = mMusicViewModel

    override fun onCreate() {
        super.onCreate()
        mMusicViewModel = MusicViewModel()
        mMusicManager = MusicManager()
        registerChanel()

    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d("onBind", "inbinder")
        return MyBinder(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return Service.START_REDELIVER_INTENT
    }
//    fun searchSong(name: String) {
//         getModel().searchSong(name)
//
//    }
//
//    fun getFullLinkSong(position: Int) {
//        getModel().getFullLinkOnline(
//            getModel().listMusicOnline.value!![position].linkSong!!
//        )
//    }
    fun playMusic(item: SongM,pos: Int) {
        mMusicManager?.setData(this, item.linkMusic) {
            createNotificationMusic(item,pos)
        }

        createNotificationMusic(item,pos)
        Log.d("posiService",pos.toString())
    }

    fun pauseMusic(item: SongM,pos: Int) {
        mMusicManager?.pause()
        createNotificationMusic(item,pos)

    }


    fun continuePlayMusic(item: SongM,pos: Int) {
        mMusicManager?.continuePlay()
        createNotificationMusic(item,pos)
    }

    fun stopMusic(item: SongM) {
        mMusicManager?.stop()

    }


    class MyBinder : Binder {
        var getService: MusicService

        constructor(musicService: MusicService) {
            this.getService = musicService
        }
    }

    private fun registerChanel() {
        val mNotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "MusicService",
                "MusicService",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "YOUR_NOTIFICATION_CHANNEL_DESCRIPTION"
            mNotificationManager.createNotificationChannel(channel)

        }
    }

    private fun createNotificationMusic(item: SongM,pos:Int) {
        Log.d("musicOnNotification", item.toString())
        musicFromService.value = item
        currentPos.value=pos
        val notification = NotificationCompat.Builder(
            this,
            "MusicService"
        )
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags =  Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

        val intentContentActivity = PendingIntent.getActivity(this, 1, intent, 0)
        val intentBroadPlay = Intent().setAction(ACTION_PLAY)
        val actionIntentPlay =
            PendingIntent.getBroadcast(this, 0, intentBroadPlay, PendingIntent.FLAG_UPDATE_CURRENT)
        val intentBroadPrevious = Intent().setAction(ACTION_PREVIOUS)
        val actionIntentPrevious = PendingIntent.getBroadcast(
            this,
            0,
            intentBroadPrevious,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val intentBroadNext = Intent().setAction(ACTION_NEXT)
        val actionIntentNext = PendingIntent.getBroadcast(this,
            0,
            intentBroadNext,
            PendingIntent.FLAG_UPDATE_CURRENT)
        val intentBroadClose = Intent().setAction(ACTION_CLOSE)
        val actionIntentClose = PendingIntent.getBroadcast(this,
            0,
            intentBroadClose,
            PendingIntent.FLAG_UPDATE_CURRENT)
        notification.addAction(
            R.drawable.ic_baseline_skip_previous_24,
            "previous",
            actionIntentPrevious
        )
        notification.addAction(
            if (mMusicManager?.mMediaPlayer!!.isPlaying) R.drawable.ic_baseline_pause_24 else R.drawable.ic_baseline_play_arrow_24,
            "play",
            actionIntentPlay
        )
        notification.addAction(R.drawable.ic_baseline_skip_next_24, "next", actionIntentNext)
        notification.addAction(R.drawable.ic_baseline_close_24, "Close", actionIntentClose)
        notification.setContentIntent(intentContentActivity)
        notification.setContentTitle("Music From Phúc")
        notification.setContentText(item.songName)
        notification.setSmallIcon(R.drawable.ic_baseline_library_music_24)
        notification.setAutoCancel(true)
        notification.priority = NotificationCompat.PRIORITY_LOW
        notification.setStyle(androidx.media.app.NotificationCompat.MediaStyle())
        notification.setLargeIcon(
            BitmapFactory.decodeResource(
                resources,
                R.drawable.musicnew
            )
        )
        startForeground(10, notification.build())

    }

    override fun onDestroy() {
        Log.d("destroyService", "ondestroy")
        super.onDestroy()
        mMusicManager?.stop()
        mMusicManager?.release()

    }

}