package com.example.weatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.models.WeatherResponse
import com.example.weatherapp.network.WeatherService
import com.google.android.gms.location.*
import com.google.gson.Gson
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import retrofit.GsonConverterFactory
import retrofit.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private var mProgressDialog: Dialog? = null
    private lateinit var mSharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        checkPermission()

        mSharedPreferences = getSharedPreferences(Constant.PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    // TODO (Check here whether GPS is ON or OFF using the method which we have created)
    // START
    private fun checkPermission(): Unit {
        if (isLocationEnabled()) {
            checkPermissionLocationDexter()
        } else {
            showToastText("Your location provider is turned off. Please turn it on.")

            // This will redirect you to settings from where you need to turn on the location provider.
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
    }

    // TODO (Asking the location permission on runtime.)
    // START
    private fun checkPermissionLocationDexter() {
        Dexter.withActivity(this).withPermissions(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ).withListener(
            object: MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (report!!.areAllPermissionsGranted()) {
                        requestLocationData()
                    }

                    if (report.isAnyPermissionPermanentlyDenied) {
                        showToastText("You have denied location permission.")
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    // TODO show dialog rationale dialog for permission
                    showRationaleDialogPermissions()
                }
            }
        ).onSameThread().check()
    }

    // TODO (A alert dialog for denied permissions and if needed to allow it from the settings app info.)
    // START
    /**
     * A function used to show the alert dialog when the permissions are denied and need to allow it from settings app info.
     */
    private fun showRationaleDialogPermissions() {
        AlertDialog.Builder(this)
            .setMessage("It looks like you have turned off permissions")
                // _ => parameter is not used
            .setPositiveButton("GO TO SETTINGS") { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    /**
     * A function which is used to verify that the location or GPS is enable or not of the user's device.
     */
    private fun isLocationEnabled(): Boolean {
        // This provide access to system location services.
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }

    // TODO (Add a function to get the location of the device using the fusedLocationProviderClient.)
    // START
    /**
     * A function to request the current location. Using the fused location provider client.
     */
    @SuppressLint("MissingPermission")
    private fun requestLocationData() {

        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        Looper.myLooper()?.let {
            mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                it
            )
        }
    }

    // TODO (Register a request location callback to get the location.)
    // START
    /**
     * A location callback object of fused location provider client where we will get the current location details.
     */
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location = locationResult.lastLocation
            val latitude = mLastLocation.latitude

            val longitude = mLastLocation.longitude

            // Pass the latitude and longitude as parameters in function
            getLocationWeatherDetails(latitude, longitude)
        }
    }

    // TODO (Get the weather if have internet connection)
    /**
     * Check connection
     * Call the API to get the weather based on location
     */
    private fun getLocationWeatherDetails(latitude: Double, longitude: Double) {
        if (Constant.isNetworkAvailable(this)) {
            showToastText("You have connected to the internet.")
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                /** Add converter factory for serialization and deserialization of objects. */
                /**
                 * Create an instance using a default {@link Gson} instance for conversion. Encoding to JSON and
                 * decoding from JSON (when no charset is specified by a header) will use UTF-8.
                 */
                .addConverterFactory(GsonConverterFactory.create())
                /** Create the Retrofit instances. */
                .build()

            // Further step for API call
            // START
            /**
             * Here we map the service interface in which we declares the end point and the API type
             *i.e GET, POST and so on along with the request parameter which are required.
             */
            val service: WeatherService = retrofit.create<WeatherService>(
                WeatherService::class.java
            )

            /** An invocation of a Retrofit method that sends a request to a web-server and returns a response.
             * Here we pass the required param in the service
             */
            val listCall: Call<WeatherResponse> = service.getWeather(
                latitude, longitude, Constant.METRIC_UNIT, Constant.APP_ID
            )

            // Show the progress dialog
            // START
            // Used to show the progress dialog
            showCustomProgressDialog()
            // END

            // Callback methods are executed using the Retrofit callback executor.
            listCall.enqueue(object: Callback<WeatherResponse>{
                override fun onResponse(response: Response<WeatherResponse>?, retrofit: Retrofit?) {
                    // Check weather the response is success or not.
                    if(response!!.isSuccess){
                        hideProgressDialog()
                        /** The de-serialized response body of a successful response. */
                        val weatherList: WeatherResponse = response.body()

                        /** Store weather list into shared preferences. */
                        val weatherResponseJsonString = Gson().toJson(weatherList)
                        val editor = mSharedPreferences.edit()
                        editor.putString(Constant.WEATHER_RESPONSE_DATA, weatherResponseJsonString)
                        editor.apply()

                        /** Setup The UI */
                        setupUI()
                        Log.i("Response result ", "${weatherList}")
                    } else {
                        // If the response is not success then we check the response code.
                        when(response.code()) {
                            400 -> {
                                Log.e("Eror 400", "Bad Connection")
                            }
                            404 -> {
                                Log.e("Error 404", "Not Found")
                            }
                            else -> {
                                Log.e("Error ", "Generic Error")
                            }
                        }
                    }
                }

                override fun onFailure(t: Throwable?) {
                    // Hide the progress dialog
                    // START
                    hideProgressDialog() // Hides the progress dialog
                    // END
                    Log.e("Error Failure", t!!.message.toString())
                }
            })
        } else {
            showToastText("No internet connection available.")
        }
    }

    private fun showToastText(
        text: String
    ) {
        Toast.makeText(
            this@MainActivity,
            text,
            Toast.LENGTH_SHORT
        ).show()
    }

    // TODO (STEP 5: Create a functions for SHOW and HIDE progress dialog.)
    // START
    /**
     * Method is used to show the Custom Progress Dialog.
     */
    private fun showCustomProgressDialog() {
        mProgressDialog = Dialog(this)

        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        mProgressDialog!!.setContentView(R.layout.dialog_custom_progress)

        //Start the dialog and display it on screen.
        mProgressDialog!!.show()
    }

    /**
     * This function is used to dismiss the progress dialog if it is visible to user.
     */
    private fun hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog!!.dismiss()
        }
    }
    // END

    private fun setupUI() {
        /** Get the value from sharedPreferences */
        val weatherResponseJsonString = mSharedPreferences.getString(Constant.WEATHER_RESPONSE_DATA, "")
        if (!weatherResponseJsonString.isNullOrEmpty()) {
            /** Convert to JSON WeatherResponse */
            val weatherList = Gson().fromJson(weatherResponseJsonString, WeatherResponse::class.java)

            for (i in weatherList.weather.indices){
                binding?.tvMain?.text = weatherList.weather[i].main
                binding?.tvMainDescription?.text = weatherList.weather[i].description
                binding?.tvTemp?.text = weatherList.main.temp.toString() + getUnit(getLocale())

                binding?.tvSunriseTime?.text = unitTime(weatherList.sys.sunrise)
                binding?.tvSunsetTime?.text = unitTime(weatherList.sys.sunset)

                binding?.tvMax?.text = weatherList.main.temp_max.toString()
                binding?.tvMin?.text = weatherList.main.temp_min.toString()

                binding?.tvHumidity?.text = weatherList.main.humidity.toString() + " per cent"
                binding?.tvSpeed?.text = weatherList.wind.speed.toString()
                binding?.tvName?.text = weatherList.name
                binding?.tvCountry?.text = weatherList.sys.country

                when(weatherList.weather[i].icon){
                    "01d" -> binding?.ivMain?.setImageResource(R.drawable.sunny)
                    "02d" -> binding?.ivMain?.setImageResource(R.drawable.cloud)
                    "03d" -> binding?.ivMain?.setImageResource(R.drawable.cloud)
                    "04d" -> binding?.ivMain?.setImageResource(R.drawable.cloud)
                    "04n" -> binding?.ivMain?.setImageResource(R.drawable.cloud)
                    "10d" -> binding?.ivMain?.setImageResource(R.drawable.rain)
                    "11d" -> binding?.ivMain?.setImageResource(R.drawable.storm)
                    "13d" -> binding?.ivMain?.setImageResource(R.drawable.snowflake)
                    "01n" -> binding?.ivMain?.setImageResource(R.drawable.cloud)
                    "02n" -> binding?.ivMain?.setImageResource(R.drawable.cloud)
                    "03n" -> binding?.ivMain?.setImageResource(R.drawable.cloud)
                    "10n" -> binding?.ivMain?.setImageResource(R.drawable.cloud)
                    "11n" -> binding?.ivMain?.setImageResource(R.drawable.rain)
                    "13n" -> binding?.ivMain?.setImageResource(R.drawable.snowflake)
                    "50n" -> binding?.ivMain?.setImageResource(R.drawable.mist)
                    "50d" -> binding?.ivMain?.setImageResource(R.drawable.mist)
                }
            }
        }
    }

    private fun getUnit(value: String): String? {
        var value = "°C"
        if ("US" == value || "LR" == value || "MM" == value){
            value = "°F"
        }

        return value
    }

    private fun getLocale(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            application.resources.configuration.locales.toString()
        } else {
            applicationContext.getResources().getConfiguration().locale.toString()
        }
    }

    private fun unitTime(timex: Long): String? {
        val date = Date(timex * 1000L)
        val sdf = SimpleDateFormat("HH:mm", Locale.UK)
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_refresh -> {
                requestLocationData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}