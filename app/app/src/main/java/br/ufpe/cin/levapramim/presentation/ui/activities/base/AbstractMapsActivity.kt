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
    private var mLocationCallback : LocationCallback = InnerLocationCallback(this)
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
                val locationRequest = LocationRequest()
                locationRequest.priority = PRIORITY_HIGH_ACCURACY
                locationRequest.interval = TimeUnit.MINUTES.toMillis(LOCATION_UPDATE_INTERVAL_MIN)
                mFusedLocationProviderClient?.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper())
            }
        }
    }

    class InnerLocationCallback(): LocationCallback() {
        private lateinit var activityRefference : WeakReference<AbstractMapsActivity>

        constructor(activity: AbstractMapsActivity) : this() {
            this.activityRefference = WeakReference<AbstractMapsActivity>(activity)
        }

        override fun onLocationResult(locationResult: LocationResult?) {
            val activity = activityRefference.get()!!
            super.onLocationResult(locationResult)
            if (locationResult == null) return
            val lastLocation = locationResult.lastLocation
            activity.onLocation(lastLocation)
            val latLng = LatLng(lastLocation.latitude, lastLocation.longitude)
            if (activity.shouldShowMarker()) activity.updateMarker(latLng)
            activity.updateCamera(latLng)
        }
    }

    open fun onLocation(location: Location) {
        // UNIMPLEMENTED
    }

    fun updateMarker(latLng: LatLng) {
        if (mMarker == null)
            mMarker = mMap!!.addMarker(MarkerOptions().position(latLng).title("You're here"))
        else
            mMarker!!.position = latLng
    }

    fun updateCamera(latLng: LatLng) {
        val cameraPosition = CameraPosition.fromLatLngZoom(latLng, 15.0f)
        mMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
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

    override fun onStop() {
        super.onStop()
        mFusedLocationProviderClient?.removeLocationUpdates(mLocationCallback)
    }

    protected fun getMap() = mMap

    open fun shouldShowMarker() = true
}
