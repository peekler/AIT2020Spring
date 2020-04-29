package hu.ait.aitmapdemo

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.sucho.placepicker.AddressData
import com.sucho.placepicker.Constants
import com.sucho.placepicker.MapType
import com.sucho.placepicker.PlacePicker
import kotlinx.android.synthetic.main.activity_maps.*
import java.util.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback,
    MyLocationProvider.OnNewLocationAvailable {

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

        btnPick.setOnClickListener {

        }

        requestNeededPermission()


        tvLocation.setOnClickListener {
            if (prevLocation != null){
                Thread {
                    try {
                        val gc = Geocoder(this, Locale.getDefault())
                        var addrs: List<Address> =
                            gc.getFromLocation(prevLocation!!.latitude, prevLocation!!.longitude, 3)
                        val addr =
                            "${addrs[0].getAddressLine(0)}, ${addrs[0].getAddressLine(1)}," +
                                    " ${addrs[0].getAddressLine(2)}"

                        runOnUiThread {
                            Toast.makeText(this, addr, Toast.LENGTH_LONG).show()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        runOnUiThread {
                            Toast.makeText(
                                this@MapsActivity,
                                "Error: ${e.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }.start()
            }
        }


        btnPick.setOnClickListener {
            val intent = PlacePicker.IntentBuilder()
                .setLatLong(47.0, 19.0)  // Initial Latitude and Longitude the Map will load into
                .showLatLong(true)  // Show Coordinates in the Activity
                .setMapZoom(12.0f)  // Map Zoom Level. Default: 14.0
                .setAddressRequired(true) // Set If return only Coordinates if cannot fetch Address for the coordinates. Default: True
                .setMarkerImageImageColor(R.color.colorPrimary)
                .setMapType(MapType.NORMAL) //Activate GooglePlace Search Bar. Default is false/not activated. SearchBar is a chargeable feature by Google
                .onlyCoordinates(true)  //Get only Coordinates from Place Picker
                .build(this)

            startActivityForResult(intent, Constants.PLACE_PICKER_REQUEST)

        }

    }

    override fun onActivityResult(requestCode: Int,resultCode: Int,data: Intent?) {
        if (requestCode == Constants.PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val addressData = data?.getParcelableExtra<AddressData>(Constants.ADDRESS_INTENT)
                Toast.makeText(this, addressData.toString(), Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
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
        myLocationProvider = MyLocationProvider(
                this, this
        )
        myLocationProvider.startLocationMonitoring()
    }

    override fun onStop() {
        super.onStop()
        myLocationProvider.stopLocationMonitoring()
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.isTrafficEnabled = true
        mMap.isBuildingsEnabled = true

        val mapStyleOptions =
                MapStyleOptions.loadRawResourceStyle(this, R.raw.custom_map_style)
        mMap.setMapStyle(mapStyleOptions)




        val polyRect: PolygonOptions = PolygonOptions().add(
                LatLng(44.0, 19.0),
                LatLng(44.0, 26.0),
                LatLng(48.0, 26.0),
                LatLng(48.0, 19.0))
        val polygon: Polygon = mMap.addPolygon(polyRect)
        polygon.fillColor = Color.argb(25, 0, 255, 0)

        val polyLineOpts = PolylineOptions().add(
                LatLng(54.0, 19.0),
                LatLng(54.0, 26.0),
                LatLng(58.0, 26.0))
        val polyline = mMap.addPolyline(polyLineOpts)

        polyline.color = Color.GREEN




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

            mMap.animateCamera(CameraUpdateFactory.newLatLng(it))

/*            val random = Random(System.currentTimeMillis())
            val cameraPostion = CameraPosition.Builder()
                    .target(it)
                    .zoom(10f+random.nextInt(5))
                    .tilt(30f+random.nextInt(15))
                    .bearing(45f+random.nextInt(45))
                    .build()

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPostion))*/
        }

        mMap.setOnMarkerClickListener(object : GoogleMap.OnMarkerClickListener{
            override fun onMarkerClick(marker: Marker?): Boolean {
                Toast.makeText(this@MapsActivity, marker?.title, Toast.LENGTH_LONG).show()

                return true
            }
        })

        mMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener{
            override fun onMarkerDragEnd(marker: Marker?) {
                Toast.makeText(this@MapsActivity, "DRAG ENDED", Toast.LENGTH_LONG).show()
            }

            override fun onMarkerDragStart(p0: Marker?) {

            }

            override fun onMarkerDrag(p0: Marker?) {

            }
        })
    }

    var prevLocation: Location? = null
    var distance: Float = 0f

    override fun onNewLocation(location: Location) {
        if (location.accuracy < 25) {
            if (prevLocation != null) {
                if (location.distanceTo(prevLocation)>3) {
                    distance += location.distanceTo(prevLocation)
                }
            }
            prevLocation = location
        }

        tvLocation.text = """
            Provider: ${location.provider}
            Lat: ${location.latitude}
            Lng: ${location.longitude}
            Accuracy: ${location.accuracy}
            Distance: ${distance} m
            Altitude ${location.altitude}
            Speed: ${location.speed}
        """.trimIndent()

        mMap.animateCamera(CameraUpdateFactory.newLatLng(LatLng(location.latitude, location.longitude)))
    }
}
