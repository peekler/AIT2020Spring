package hu.ait.broadcastreceiverdemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class OutCallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val outNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER)

        Toast.makeText(context, outNumber, Toast.LENGTH_LONG).show()
        //this.resultData = "123"
    }
}
