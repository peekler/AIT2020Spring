package hu.ait.aitmapdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        btnNormal.setOnClickListener {
            mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        }
        btnSat.setOnClickListener {
            mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.isTrafficEnabled = true
        mMap.isBuildingsEnabled = true

        // Add a marker in Sydney and move the camera
        val markerCoord = LatLng(47.0, 19.0)
        mMap.addMarker(MarkerOptions().position(markerCoord).title("Marker in Hungary"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(markerCoord))


        mMap.setOnMapClickListener {
            var newMarker = mMap.addMarker(
                    MarkerOptions()
                            .position(it)
                            .title("My marker")
                            .snippet("long text of the marker")
            )

            newMarker.isDraggable = true

            //mMap.animateCamera(CameraUpdateFactory.newLatLng(it))

            val random = Random(System.currentTimeMillis())
            val cameraPostion = CameraPosition.Builder()
                    .target(it)
                    .zoom(10f+random.nextInt(5))
                    .tilt(30f+random.nextInt(15))
                    .bearing(45f+random.nextInt(45))
                    .build()

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPostion))
        }
    }
}
