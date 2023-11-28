package com.google.mediapipe.examples.gesturerecognizer.Phillipshueapi

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.pow

//Viewmodel to handle the api call
class HueApiViewModel(context: Context): ViewModel() {
    val retrofit = Retrofit.Builder()
        .baseUrl("YOUR HUE BRIDGE IP ADDRESS") // Bridge ip address
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val lightslivedata = MutableLiveData<List<HueLight>?>()


    val hueservices = retrofit.create(HueServices::class.java)
    var idlist: MutableList<String> = mutableListOf()
    var lightid: String? = null

    private val sharedPref = context.getSharedPreferences("hue_pref",Context.MODE_PRIVATE)

    // Will retrieve the username from the shared preferences which allows it to keep the same username for the bridge
    var userName: String? = sharedPref.getString("USERNAME_KEY",null)

    init {
        if (userName == null){
           Log.d("Username Warning", "Press the button on the hue bridge")
        } else {
            //Will immediately get the light info after we grabbed the usrname
            fetchlights()
        }
    }

    fun getusername(devicetype: String?) {
        hueservices.createUser(Devicetype("HomeBridge")).enqueue(object: Callback<List<SuccessResponse>>{
            override fun onResponse(
                call: Call<List<SuccessResponse>>,
                response: Response<List<SuccessResponse>>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null && responseBody.isNotEmpty()) {
                    userName = responseBody[0].success.username
                    with(sharedPref.edit()){
                        putString("USERNAME_KEY", userName)
                        apply()
                    }
                }

                if (userName != null) {
                    Log.d("Username", "username found: $userName")
                } else {
                    Log.e("Username", "Username not found")
                }
            }

            override fun onFailure(call: Call<List<SuccessResponse>>, t: Throwable) {
                Log.e("Username Failure", "Username failed to be retrieved: $t")
            }

        })
    }

    fun fetchlights() {
        hueservices.getLight("$userName").enqueue(object: Callback<Map<String,HueLight>>{
            override fun onResponse(
                call: Call<Map<String, HueLight>>, response: Response<Map<String, HueLight>>) {
                if (response.isSuccessful){
                    val lights = response.body()
                    lights.let { lightsmap ->
                        val lightsList = lightsmap?.values?.toList()
                        lightslivedata.value = lightsList
                    }

                    if (lights != null && !lights.isNullOrEmpty()) {

                        lightid = lights.keys.first()
                        Log.d( "light data", "Light ID/data retrieved: $response")
                    } else {
                        Log.e("No light data", "Failed to get light id: $response")
                    }
                }
            }

            override fun onFailure(call: Call<Map<String, HueLight>>, t: Throwable) {
                Log.e("Failed to make light Call", "Light network call failed: $t")
            }
        })
    }



    fun togglelight(isOn: Boolean?){
        if (lightid == null) {
            return
        }

        // let them be null for now
        val lightstates = LightState(isOn,null,null,null)


        hueservices.updatelightState("$userName",lightid,lightstates).enqueue(object: Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful) {
                    // Successfully updated light state
                    Log.d("HueActivity Success", "Successfully updated light state")
                } else {
                    // Handle error...
                    Log.e("HueActivity Success", "Failed to update light state: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("HueActivity Failure", "Network error occurred: ${t.message}")
            }
        })
    }

    //Color conversion helper functions
    fun hextoRgb(hexColor:String): Triple <Int,Int,Int>{

        val color = if (hexColor.startsWith("#")) hexColor.substring(1) else hexColor
        val r = Integer.parseInt(color.substring(0, 2), 16)
        val g = Integer.parseInt(color.substring(2, 4), 16)
        val b = Integer.parseInt(color.substring(4, 6), 16)

        return Triple(r, g, b)
    }



    fun convertRGBtoXY(red: Int, green: Int, blue: Int): List<Float> {
        // Normalize the RGB values by dividing them by 255
        var r = red.toFloat() / 255
        var g = green.toFloat() / 255
        var b = blue.toFloat() / 255

        // Apply a gamma correction to the RGB values
        r = if (r > 0.04045) ((r + 0.055) / (1.0 + 0.055)).pow(2.4).toFloat() else (r / 12.92).toFloat()
        g = if (g > 0.04045) ((g + 0.055) / (1.0 + 0.055)).pow(2.4).toFloat() else (g / 12.92).toFloat()
        b = if (b > 0.04045) ((b + 0.055) / (1.0 + 0.055)).pow(2.4).toFloat() else (b / 12.92).toFloat()

        // Convert the RGB values to XYZ using the Wide RGB D65 conversion formula
        val x = r * 0.664511 + g * 0.154324 + b * 0.162028
        val y = r * 0.283881 + g * 0.668433 + b * 0.047685
        val z = r * 0.000088 + g * 0.072310 + b * 0.986039

        // Calculate the xy values from the XYZ values
        val xy = Pair(
            (x / (x + y + z)).toFloat(),
            (y / (x + y + z)).toFloat()
        )

        Log.i("xy Conversion", "convertRGBtoXY: $xy")
        return listOf(
            (x / (x + y + z)).toFloat(),
            (y / (x + y + z)).toFloat()
        )
    }

    fun updateLightColor(hexColor: String) {
        val (r, g, b) = hextoRgb(hexColor)
        val xyColor = convertRGBtoXY(r, g, b)
        changelightcolor(xyColor)
    }
    //Previous - Helper functions for xy color conversion

    //Put the coverison in here

    // call the viewmodel to get the conversions first,
    //then call the viewmodel to change the color
    //but still where to put the color picker dialog?


    fun changelightcolor(color: List<Float>) {



        val lightstate = LightState(null, null, xy = color, null)

        hueservices.updatelightState("$userName",lightid,lightstate).enqueue(object :Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("HueApiViewModel", "Color successfully updated for light ID: $lightid")
                } else {
                    Log.e("HueApiViewModel", "Failed to update color: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("HueApiViewModel", "Error occurred while updating color: ${t.message}")
            }

        })

    }

    fun setLightBrightness(brightness: Int) {
        // Ensure the brightness value is within the allowed range of 1 to 254
        val safeBrightness = brightness.coerceIn(0, 254)

        // Construct the LightState object with the new brightness
        val lightState = LightState(on = null, bri = safeBrightness, xy = null, sat = null)

        // Use the HueServices to send the PUT request
        hueservices.updatelightState("$userName", lightid, lightState).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("HueApiViewModel", "Brightness successfully updated for light ID: $lightid")
                } else {
                    Log.e("HueApiViewModel", "Failed to update brightness: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("HueApiViewModel", "Error occurred while updating brightness: ${t.message}")
            }
        })
    }
}
