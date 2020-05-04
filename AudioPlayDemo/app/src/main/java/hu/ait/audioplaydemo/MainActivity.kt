package hu.ait.audioplaydemo

import android.media.MediaPlayer
import android.media.RingtoneManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MediaPlayer.OnPreparedListener {

    private lateinit var mediaPlayer: MediaPlayer

    var playStarted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPlayTone.setOnClickListener {
            //playNotifTone()
            if (!playStarted) {
                mediaPlayer = MediaPlayer.create(this,
                        R.raw.demo)
                mediaPlayer.setOnPreparedListener(this)
            }
        }

        btnStop.setOnClickListener {
            mediaPlayer?.stop()
        }
    }

    override fun onPrepared(mp: MediaPlayer?) {
        if (!mediaPlayer.isPlaying){
            mediaPlayer.start()
            playStarted = true
        }
    }

    override fun onStop() {
        mediaPlayer?.stop()
        super.onStop()
    }

    private fun playNotifTone() {
        val uriTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        val ringTone = RingtoneManager.getRingtone(applicationContext, uriTone)
        ringTone.play()
    }
}
