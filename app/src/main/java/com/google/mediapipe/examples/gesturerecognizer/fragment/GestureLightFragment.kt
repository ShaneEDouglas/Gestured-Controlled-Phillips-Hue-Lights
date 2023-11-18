package com.google.mediapipe.examples.gesturerecognizer.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.fragment.app.Fragment
import com.google.mediapipe.examples.gesturerecognizer.MainActivity
import com.google.mediapipe.examples.gesturerecognizer.Phillipshueapi.HueApiViewModel
import com.google.mediapipe.examples.gesturerecognizer.R

class GestureLightFragment: Fragment(R.layout.fragment_lightgesture) {
    val viewModel: HueApiViewModel by lazy {
        HueApiViewModel(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lightbtn = view.findViewById<CardView>(R.id.light_up_card)


        lightbtn.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
    }
}