package br.ufpe.cin.levapramim.presentation.ui.activities.base

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityCompat.checkSelfPermission
import android.widget.Toast
import br.ufpe.cin.levapramim.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.ref.WeakReference
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

abstract class AbstractMapsActivity : AbstractLoggedActivity(), OnMapReadyCallback {
    private val RC_FINE_LOCATION_PERMISSION = 0
    private val LOCATION_UPDATE_INTERVAL_MIN = 1L
    private var mFusedLocationProviderClient : FusedLocationProviderClient? = null
    private var mMap : GoogleMap? = null
    private var mMarker : Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResourceId())
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    abstract fun getLayoutResourceId() : Int

    override fun onResume() {
        super.onResume()
        if (currentUser != null) {
            startCurrentLocationUpdates()
        }
    }

    @SuppressLint("MissingPermission")
    fun startCurrentLocationUpdates() {
        if (isGooglePlayServicesAvailable()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                !werePermissionsGranted()) {
                ActivityCompat.requestPermissions(this,
                    arrayOf(ACCESS_FINE_LOCATION),
                    RC_FINE_LOCATION_PERMISSION)
            } else {
                mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                mFusedLocationProviderClient!!.lastLocation
                    .addOnSuccessListener(this::onLocation)
            }
        }
    }

    open fun onLocation(location: Location) {
        val latLng = LatLng(location.latitude, location.longitude)
        updateCamera(latLng)
    }

    fun updateCamera(latLng: LatLng) {
        val cameraPosition = CameraPosition.fromLatLngZoom(latLng, 15.0f)
        mMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.isMyLocationEnabled = true
        mMap = googleMap
    }

    fun isGooglePlayServicesAvailable() : Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val status = googleApiAvailability.isGooglePlayServicesAvailable(this)
        if (status == ConnectionResult.SUCCESS) {
            return true
        } else {
            if (googleApiAvailability.isUserResolvableError(status)) {
                Toast.makeText(this, "Please install Google Play Services to use this app", Toast.LENGTH_LONG).show()
            }
            return false
        }
    }

    fun werePermissionsGranted() : Boolean {
        return checkSelfPermission(this, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED ||
                checkSelfPermission(this, ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED
    }

    protected fun getMap() = mMap
}
