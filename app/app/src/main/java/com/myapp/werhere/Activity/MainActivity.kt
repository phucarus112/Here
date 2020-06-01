package com.myapp.werhere.Activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.android.material.navigation.NavigationView
import com.myapp.werhere.R
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.myapp.werhere.Adapter.PlaceAdapter
import com.myapp.werhere.Fragment.Setting
import com.myapp.werhere.Object.Place
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_table_result.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.jar.Manifest

class MainActivity : AppCompatActivity(), OnMapReadyCallback{

    override fun onMapReady(p0: GoogleMap?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var locationManager : LocationManager? = null
    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: Int = 100
    var supportMapFragment: SupportMapFragment? =null
    var checkState: Boolean= false
    var typeKeyword: String = ""
    var mylat: Double = 0.0
    var myLng: Double = 0.0
    var progressDialog: ProgressDialog? = null
    var placeList: ArrayList<Place> = ArrayList()
    var client: FusedLocationProviderClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        progressDialog = ProgressDialog(this)
        customChipNavigationBar()
        option.setOnClickListener() {
            //setEventClickForOption()
        }
        client = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentPosition()
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }

    private fun getCurrentPosition() {
       var task : Task<Location> = client!!.lastLocation
       task.addOnSuccessListener(object: OnSuccessListener<Location>{
           override fun onSuccess(location: Location?) {
               if(location != null){
                   supportMapFragment!!.getMapAsync(object: OnMapReadyCallback{
                       override fun onMapReady(p0: GoogleMap?) {
                           var latlng: LatLng = LatLng(location.latitude,location.longitude)
                           var marker: MarkerOptions = MarkerOptions().position(latlng).title("Here")
                           p0!!.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,10F))
                           p0!!.addMarker(marker)
                       }

                   })
               }
           }

       })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION ->{
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getCurrentPosition()
                }
            }
        }
    }

    /*private fun setEventClickForOption() {
        drawer_layout.openDrawer(Gravity.LEFT)
        nv.setNavigationItemSelectedListener(object: NavigationView.OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when(item.itemId) {
                    R.id.nv_airport -> {
                        typeKeyword = "airport"
                        handleFindPlaces()
                    }
                    R.id.nv_atm -> {
                        typeKeyword = "atm"
                        handleFindPlaces()
                    }
                    R.id.nv_bank -> {
                        typeKeyword = "bank"
                        handleFindPlaces()
                    }
                    R.id.nv_bar -> {
                        typeKeyword = "bar"
                        handleFindPlaces()
                    }
                    R.id.nv_bookstore -> {
                        typeKeyword = "book_store"
                        handleFindPlaces()
                    }
                    R.id.nv_busstation -> {
                        typeKeyword = "bus_station"
                        handleFindPlaces()
                    }
                    R.id.nv_cafe -> {
                        typeKeyword = "cafe"
                        handleFindPlaces()
                    }
                    R.id.nv_church -> {
                        typeKeyword = "church"
                        handleFindPlaces()
                    }
                    R.id.nv_dentist -> {
                        typeKeyword = "dentist"
                        handleFindPlaces()
                    }
                    R.id.nv_drugstore -> {
                        typeKeyword = "drug_store"
                        handleFindPlaces()
                    }
                    R.id.nv_florist -> {
                        typeKeyword = "florist"
                        handleFindPlaces()
                    }
                    R.id.nv_gym -> {
                        typeKeyword = "gym"
                        handleFindPlaces()
                    }
                    R.id.nv_hospital -> {
                        typeKeyword = "hospital"
                        handleFindPlaces()
                    }
                    R.id.nv_library -> {
                        typeKeyword = "library"
                        handleFindPlaces()
                    }
                    R.id.nv_movie_theater -> {
                        typeKeyword = "movie_theater"
                        handleFindPlaces()
                    }
                    R.id.nv_museum-> {
                        typeKeyword = "museum"
                        handleFindPlaces()
                    }
                    R.id.nv_park -> {
                        typeKeyword = "park"
                        handleFindPlaces()
                    }
                    R.id.nv_parking -> {
                        typeKeyword = "parking"
                        handleFindPlaces()
                    }
                    R.id.nv_police -> {
                        typeKeyword = "police"
                        handleFindPlaces()
                    }
                    R.id.nv_school -> {
                        typeKeyword = "school"
                        handleFindPlaces()
                    }
                    R.id.nv_shopping_mall -> {
                        typeKeyword = "shopping_mall"
                        handleFindPlaces()
                    }
                    R.id.nv_spa -> {
                        typeKeyword = "spa"
                        handleFindPlaces()
                    }
                    R.id.nv_stadium -> {
                        typeKeyword = "stadium"
                        handleFindPlaces()
                    }
                    R.id.nv_store -> {
                        typeKeyword = "store"
                        handleFindPlaces()
                    }
                    R.id.nv_supermarket -> {
                        typeKeyword = "supermarket"
                        handleFindPlaces()
                    }
                    R.id.nv_zoo -> {
                        typeKeyword = "zoo"
                        handleFindPlaces()
                    }
                    R.id.nv_university -> {
                        typeKeyword = "university"
                        handleFindPlaces()
                    }
                }
                return false
            }
        })
    }

    private fun handleFindPlaces() {
        when(check_GPS_Network_State()) {
            -1 ->Toast.makeText(this@MainActivity,"Bạn chưa bật GPS",Toast.LENGTH_SHORT).show()
            0 -> Toast.makeText(this@MainActivity,"Internet không có sẵn",Toast.LENGTH_SHORT).show()
            1 -> {
                progressDialog!!.setMessage("Đang xử lý")
                progressDialog!!.show()
                progressDialog!!.setCanceledOnTouchOutside(false)
                var url: String = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+ mylat.toString() + "," + myLng.toString() + "&radius=5000&type=" + typeKeyword + "&key="+R.string.map_api_key
                var requestQueue = Volley.newRequestQueue(this)
                var jsonObjectRequest = JsonObjectRequest(Request.Method.GET,url,null,Response.Listener {
                    response ->
                            var dialog = Dialog(this)
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                            dialog.setContentView(R.layout.dialog_table_result)
                            dialog.setCanceledOnTouchOutside(false)
                            dialog.show()
                            var back : ImageView = dialog.findViewById(R.id.back_dialog) as ImageView
                            var num  : TextView = dialog.findViewById(R.id.num_result) as TextView
                            var listview: ListView = dialog.findViewById(R.id.lv) as ListView
                            back.setOnClickListener {
                                dialog.dismiss()
                            }
                            var status: String = response.getString("status")
                            if(status.equals("OK") == true) {

                                Toast.makeText(this@MainActivity,response.toString(),Toast.LENGTH_SHORT).show()
                                placeList.clear()
                                var result: JSONArray = response.getJSONArray("results")
                                for(i in 0..result.length()-1){
                                    var index: JSONObject = result.getJSONObject(i)
                                    var geometry : JSONObject = index.getJSONObject("geometry")
                                    var location: JSONObject = geometry.getJSONObject("location")
                                    var lat: Double = location.getDouble("lat")
                                    var lng: Double = location.getDouble("lng")
                                    var icon: String = index.getString("icon")
                                    var id: String = index.getString("id")
                                    var name: String = index.getString("name")
                                    var place_id: String = index.getString("place_id")
                                    var rating: Double = index.getDouble("rating")
                                    var user_ratings_total: Int = index.getInt("user_ratings_total")
                                    var address: String = index.getString("vicinity")
                                    placeList.add(Place(id,place_id,lat,lng,name,icon,rating,user_ratings_total,address))
                                }

                                listview.adapter = PlaceAdapter(this@MainActivity,placeList)
                                num.text = placeList.size.toString() + " kết quả tìm thấy"
                                progressDialog!!.dismiss()
                            }else{
                                num.text = "0 kết quả tìm thấy"
                                progressDialog!!.dismiss()
                            }
                }, Response.ErrorListener {
                    error -> error.printStackTrace()
                })
                requestQueue.add(jsonObjectRequest)
            }
        }
    }*/

    private fun customChipNavigationBar() {
        cnv.setItemSelected(R.id.find_choice, true)
        cnv.setOnItemSelectedListener(
            object : ChipNavigationBar.OnItemSelectedListener {
                override fun onItemSelected(i: Int) {
                    when (i) {
                    R.id.find_choice -> {
                        layout1.visibility = View.VISIBLE;
                        cnv.setItemSelected(R.id.find_choice, true)
                        fragmentManager.popBackStack();
                    }
                    R.id.setting_choice -> {
                        layout1.visibility = View.INVISIBLE;
                        cnv.setItemSelected(R.id.setting_choice, true)
                        val settingTransaction = fragmentManager.beginTransaction()
                        settingTransaction.replace(R.id.frameLayout, Setting())
                        settingTransaction.addToBackStack("setting")
                        settingTransaction.commit()
                    }
                }
            }
        })
    }

    override fun onBackPressed() {
        if(drawer_layout.isDrawerOpen(Gravity.LEFT)){
            drawer_layout.closeDrawer(Gravity.LEFT)
        }else  finish()
    }

    private fun check_GPS_Network_State(): Int{
        var gps_enabled: Boolean = false
        var network_enabled : Boolean = false

        try {
            gps_enabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }catch (e: Exception){ }

        try {
            network_enabled = locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        }catch (e: Exception){ }

        if(!gps_enabled)return -1
        if(!network_enabled)return 0
        return 1
    }
}





