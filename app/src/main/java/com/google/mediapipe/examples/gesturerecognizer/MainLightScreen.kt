package com.google.mediapipe.examples.gesturerecognizer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.mediapipe.examples.gesturerecognizer.Phillipshueapi.HueApiViewModel
import com.google.mediapipe.examples.gesturerecognizer.fragment.GestureLightFragment
import com.google.mediapipe.examples.gesturerecognizer.fragment.HelpFragment
import com.google.mediapipe.examples.gesturerecognizer.fragment.LightFragment

class MainLightScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_light_screen)

        val bottomnav: BottomNavigationView = findViewById(R.id.navigation)

        if (savedInstanceState == null){
            replaceFragment(GestureLightFragment())
        }

        bottomnav.setOnItemSelectedListener { item ->

            when (item.itemId) {
                R.id.Navigation_gesture -> {
                    replaceFragment(GestureLightFragment())
                    true
                }
                R.id.Navigation_light_control -> {
                    replaceFragment(LightFragment())
                    true
                }



                else -> {false}
            }
        }



    }

    //Replace the current fragment when tapped
    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}

private fun sayhi(nums:List<String>){
    nums.forEach {
        println("hi")
    }
}
