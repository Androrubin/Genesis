package com.androrubin.genesis.map_work

import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.androrubin.genesis.Common.Common
import com.androrubin.genesis.Model.MyPlaces
import com.androrubin.genesis.R
import com.androrubin.genesis.Remote.GoogleAPIService
import com.androrubin.genesis.databinding.ActivityMapsBinding
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var latitude:Double=0.toDouble()
    private var longitude:Double=0.toDouble()

    private lateinit var mLastLocation:Location
    private var mMarker:Marker?=null

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback

    lateinit  var mService:GoogleAPIService
    internal   var currentPlace: MyPlaces?=null

    companion object{
        private const val MY_PERMISSION_CODE :Int = 1000
    }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)




        mService = Common.googleAPIService


        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            if (checkLocationPermission()) {
                buildLocationRequest()
                buildLocationCallBack()



                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                fusedLocationProviderClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.myLooper()
                )
            }
        }else
        {
            buildLocationRequest()
            buildLocationCallBack()

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.myLooper()
            )

        }

        binding.navView.setOnNavigationItemReselectedListener { item->
            when(item.itemId){
                R.id.action_hospital->nearByPlace("hospital")
            }
            true
        }



    }

    private fun nearByPlace(typePlace : String){

        mMap.clear()
        val url = getUrl(latitude,longitude,typePlace)

        mService.getNearbyPlaces(url).enqueue(object: Callback<MyPlaces>{
            override fun onResponse(call: Call<MyPlaces>, response: Response<MyPlaces>) {

                currentPlace=response.body()!!

                if(response!!.isSuccessful)
                {
                    for(i in 0 until response!!.body()!!.results!!.size){

                        val markerOptions=MarkerOptions()
                        val googlePlace = response.body()!!.results!![i]
                        val lat = googlePlace.geometry!!.location!!.lat
                        val lng = googlePlace.geometry!!.location!!.lng
                        val placeName = googlePlace.name
                        val latLng = LatLng(lng,lat)

                        markerOptions.position(latLng)
                        markerOptions.title(placeName)
                        if(typePlace.equals("hospital"))
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_name))
                        else
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))


                        markerOptions.snippet(i.toString())

                        mMap!!.addMarker(markerOptions)
                        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                        mMap!!.animateCamera(CameraUpdateFactory.zoomTo(20f))

                    }



                }




            }

            override fun onFailure(call: Call<MyPlaces>, t: Throwable) {
                Toast.makeText(baseContext,""+t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun getUrl(latitude: Double, longitude: Double, typePlace: String): String {


        val googlPlaceUrl = StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json")
        googlPlaceUrl.append("?location=$latitude,$longitude")
        googlPlaceUrl.append("&radius=10000")
        googlPlaceUrl.append("&type=$typePlace")
        googlPlaceUrl.append("&key=AIzaSyBaQsGYaXj2EAaPJugicoSu16xlPjUneoI")

        Log.d("URL_DEBUG",googlPlaceUrl.toString())
        return googlPlaceUrl.toString()
    }

    private fun buildLocationCallBack(){
        locationCallback = object : LocationCallback(){

            override fun onLocationResult(p0: LocationResult) {
                mLastLocation = p0!!.locations.get(p0!!.locations.size-1)

                if(mMarker != null){

                    mMarker!!.remove()
                }
                latitude = mLastLocation.latitude
                longitude = mLastLocation.longitude

                val latLng = LatLng(latitude,longitude)
                val markerOptions = MarkerOptions()
                    .position(latLng)
                    .title("Your position")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                mMarker = mMap!!.addMarker(markerOptions)

                mMap!!.moveCamera(CameraUpdateFactory.newLatLng((latLng)))
                mMap!!.animateCamera(CameraUpdateFactory.zoomBy(11f))
            }


        }
    }


    override fun onStop() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        super.onStop()
    }

    private fun buildLocationRequest() {

        locationRequest = LocationRequest()
        locationRequest.priority= LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval= 5000
        locationRequest.fastestInterval = 3000
        locationRequest.smallestDisplacement =10f

    }

    private fun checkLocationPermission():Boolean {

        if(ContextCompat.checkSelfPermission
                (this,android.Manifest.permission.ACCESS_FINE_LOCATION)!=
                  PackageManager.PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.ACCESS_FINE_LOCATION))
                ActivityCompat.requestPermissions(this, arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ),MY_PERMISSION_CODE)
            else
                ActivityCompat.requestPermissions(this, arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ),MY_PERMISSION_CODE)
             return false
        }
        else
            return true

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode)
        {
            MY_PERMISSION_CODE->{
                if(grantResults.size > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

                    if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
                        if(checkLocationPermission()){
                            mMap!!.isMyLocationEnabled=true
                        }
                }else{
                    Toast.makeText(this,"Permission Denied", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }





    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission
                        (this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    if (checkLocationPermission()) {
                        buildLocationRequest()
                        buildLocationCallBack()



                        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                        fusedLocationProviderClient.requestLocationUpdates(
                            locationRequest,
                            locationCallback,
                            Looper.myLooper()
                        )
                    }






                    mMap!!.isMyLocationEnabled = true
                }
        }else{
            mMap!!.isMyLocationEnabled = true


            mMap.uiSettings.isZoomControlsEnabled = true
        }


    }
}