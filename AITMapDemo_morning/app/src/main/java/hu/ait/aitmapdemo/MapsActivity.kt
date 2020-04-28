package hu.ait.aitmapdemo

import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_maps.*
import java.util.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback,
  MyLocationProvider.OnNewLocationAvailable{

    private lateinit var mMap: GoogleMap

    private lateinit var myLocationProvider: MyLocationProvider

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

        requestNeededPermission()
    }

    private fun requestNeededPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                Toast.makeText(
                    this,
                    "I need it for location", Toast.LENGTH_SHORT
                ).show()
            }

            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
        } else {
            startLocation()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            101 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "ACCESS_FINE_LOCATION perm granted", Toast.LENGTH_SHORT)
                        .show()

                    startLocation()
                } else {
                    Toast.makeText(
                        this,
                        "ACCESS_FINE_LOCATION perm NOT granted",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    fun startLocation() {
        myLocationProvider = MyLocationProvider(this, this)
        myLocationProvider.startLocationMonitoring()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val mapStyleOptions =
            MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style)
        mMap.setMapStyle(mapStyleOptions)

        mMap.isTrafficEnabled = true
        mMap.isBuildingsEnabled = true

        // Add a marker in Sydney and move the camera
        val markerCoord = LatLng(47.0, 19.0)
        mMap.addMarker(MarkerOptions().position(markerCoord).title("Marker in Hungary"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(markerCoord))


        mMap.setOnMarkerClickListener(object : GoogleMap.OnMarkerClickListener{
            override fun onMarkerClick(p0: Marker?): Boolean {

                return true
            }
        })

        mMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener{
            override fun onMarkerDragEnd(marker: Marker?) {

            }

            override fun onMarkerDragStart(marker: Marker?) {

            }

            override fun onMarkerDrag(marker: Marker?) {

            }
        })

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

        addDrawingsToMap()
    }

    fun addDrawingsToMap() {
        val polyRect: PolygonOptions = PolygonOptions().add(
            LatLng(44.0, 19.0),
            LatLng(44.0, 26.0),
            LatLng(48.0, 26.0),
            LatLng(48.0, 19.0))
        val polygon: Polygon = mMap.addPolygon(polyRect)
        polygon.fillColor = Color.argb(20, 0, 255 ,0)

        val polyLineOpts = PolylineOptions().add(
            LatLng(54.0, 19.0),
            LatLng(54.0, 26.0),
            LatLng(58.0, 26.0))
        val polyline = mMap.addPolyline(polyLineOpts)

        polyline.color = Color.GREEN
    }

    var prevLocation: Location? = null
    var distance: Float = 0f

    override fun onNewLocation(location: Location) {
        if (location.accuracy<25) {
            if (prevLocation != null) {
                if (location.distanceTo(prevLocation) > 3) {
                    distance += location.distanceTo(prevLocation)
                }
            }
            prevLocation = location
        }

        tvLocation.text = """
            Lat: ${location.latitude}
            Lng: ${location.longitude}
            Accuracy: ${location.accuracy}
            Distance: ${distance}
        """.trimIndent()

        mMap.animateCamera(CameraUpdateFactory.newLatLng(
            LatLng(
            location.latitude, location.longitude
        )
        ))
    }

    override fun onStop() {
        super.onStop()
        myLocationProvider.stopLocationMonitoring()
    }
}
