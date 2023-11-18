package com.google.mediapipe.examples.gesturerecognizer.fragment

import android.graphics.drawable.Animatable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.model.ColorSwatch
import com.google.mediapipe.examples.gesturerecognizer.Phillipshueapi.HueApiViewModel
import com.google.mediapipe.examples.gesturerecognizer.Phillipshueapi.LightItem
import com.google.mediapipe.examples.gesturerecognizer.R
import com.google.mediapipe.examples.gesturerecognizer.adapters.LightAdapter
import top.defaults.colorpicker.ColorPickerPopup
import top.defaults.colorpicker.ColorPickerPopup.ColorPickerObserver


class LightFragment: Fragment(R.layout.fragment_lightcontrol) {

    private lateinit var lightrecyclerview: RecyclerView
    private lateinit var lightadapter: LightAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apiviewmodel = HueApiViewModel(requireContext())



        // Call Recycler View
        val apiViewModel = HueApiViewModel(requireContext())


        lightrecyclerview = view.findViewById(R.id.lightrecyclerview)
        lightadapter = LightAdapter(mutableListOf(),
            onSwitchchanged = { lightItem, isOn ->
                apiViewModel.togglelight(isOn)
            },
            onbrightnesschanged = { lightItem, brightness ->
                apiViewModel.setLightBrightness(brightness)
            },
            oncolorchanged = { lightItem ->

                    ColorPickerDialog
                        .Builder(requireContext())
                        .setTitle("Pick Color")
                        .setColorListener { _, colorHex ->
                            // Pass HEX color to ViewModel for processing and updating the light color
                            apiviewmodel.updateLightColor(colorHex)
                        }
                        .show()
            }
        )



        fun playgif(show:Boolean) {


            val loadinggif = view.findViewById<ImageView>(R.id.loadinggifImg)
            val animation = loadinggif.drawable

            Log.d("LightFragment", "playgif called with show: $show")
            if (show) {

                Glide.with(requireContext())
                    .asGif()
                    .load(R.drawable.loadinggifcrop)
                    .into(loadinggif)
                loadinggif.visibility = View.VISIBLE
            } else {

                loadinggif.visibility = View.GONE
            }
        }



        lightrecyclerview.adapter = lightadapter
        lightrecyclerview.layoutManager = LinearLayoutManager(requireContext())

        view.findViewById<TextView>(R.id.Rv_no_lights).visibility = View.VISIBLE
        lightrecyclerview.visibility = View.GONE

        apiViewModel.lightslivedata.observe(viewLifecycleOwner) { hueLights ->
            if (hueLights.isNullOrEmpty()) {
                val loadinggif = view.findViewById<ImageView>(R.id.loadinggifImg)
                loadinggif.visibility = View.VISIBLE

                Glide.with(requireContext())
                    .asGif()
                    .load(R.drawable.loadinggifcrop)
                    .into(loadinggif)

                loadinggif.visibility = View.VISIBLE

                view.findViewById<TextView>(R.id.Rv_no_lights).visibility = View.VISIBLE
                lightrecyclerview.visibility = View.GONE
                Log.i("LightFragment", "No lights found yet")

            } else {

                view.findViewById<TextView>(R.id.Rv_no_lights).visibility = View.GONE

                val loadinggif = view.findViewById<ImageView>(R.id.loadinggifImg)
                loadinggif.visibility = View.VISIBLE

                Glide.with(requireContext())
                    .asGif()
                    .load(R.drawable.loadinggifcrop)
                    .into(loadinggif)

                loadinggif.visibility = View.VISIBLE


                lightrecyclerview.visibility = View.VISIBLE

                val lightItems = hueLights.map { hueLight ->
                    LightItem(
                        name = hueLight.name,
                        on = hueLight.state?.on,
                        bri = hueLight.state?.bri,
                        xy = hueLight.state?.xy,
                        sat = hueLight.state?.sat


                    )
                }
                lightadapter.updateData(lightItems ?: emptyList())
            }

        }
    }
}