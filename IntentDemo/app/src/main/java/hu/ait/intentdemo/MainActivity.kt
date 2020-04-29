package hu.ait.intentdemo

import android.app.SearchManager
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnIntent.setOnClickListener {
            //intentSearch()
            //dialNumber()
            //intentSend()
            //intentWaze()
            intentMapStreetView()
        }
    }

    private fun intentSearch(){
        val intentWeb = Intent(Intent.ACTION_WEB_SEARCH)
        intentWeb.putExtra(SearchManager.QUERY, "Balaton")
        startActivity(intentWeb)
    }

    private fun dialNumber() {
        val intentDial = Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:+36131769"))
        startActivity(intentDial)
    }

    private fun intentSend() {
        val intentSend = Intent(Intent.ACTION_SEND)
        intentSend.type = "text/plain"
        intentSend.putExtra(Intent.EXTRA_TEXT, "Sharing something new - demo")

        intentSend.setPackage("com.facebook.katana")

        try {
            startActivity(intentSend)
        } catch (e: ActivityNotFoundException){
            e.printStackTrace()
        }

        //startActivity(Intent.createChooser(intentSend,
        //        "Select an app"))
    }

    private fun intentWaze() {
        //String wazeUri = "waze://?favorite=Home&navigate=yes";
        //val wazeUri = "waze://?ll=40.761043, -73.980545&navigate=yes"
        val wazeUri = "waze://?q=AIT&navigate=yes"

        val intentTest = Intent(Intent.ACTION_VIEW)
        intentTest.data = Uri.parse(wazeUri)
        startActivity(intentTest)
    }

    private fun intentMapStreetView(){
        val gmmIntentUri = Uri.parse("google.streetview:cbll=46.414382,10.013988")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }
}
