package hu.ait.broadcastreceiverdemo

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestNeededPermission()
    }

    override fun onStart() {
        registerReceiver(
                AirPlaneReceiver(),
                IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        )
        super.onStart()
    }

    private fun requestNeededPermission() {
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.RECEIVE_SMS)) {
                Toast.makeText(this@MainActivity,
                        "I need it for sms", Toast.LENGTH_SHORT).show()
            }

            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.RECEIVE_SMS, Manifest.permission.PROCESS_OUTGOING_CALLS),
                    101)
        } else {
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            101 -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty()
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this@MainActivity, "RECEIVE_SMS and call perm granted", Toast.LENGTH_SHORT).show()


                } else {
                    Toast.makeText(this@MainActivity,
                            "RECEIVE_SMS and call perm NOT granted", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

}
