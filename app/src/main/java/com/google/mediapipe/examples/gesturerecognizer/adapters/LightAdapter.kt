package com.google.mediapipe.examples.gesturerecognizer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.mediapipe.examples.gesturerecognizer.Phillipshueapi.LightItem
import com.google.mediapipe.examples.gesturerecognizer.R

class LightAdapter(private var lights: MutableList<LightItem>,
    private val onSwitchchanged: (LightItem, Boolean) -> Unit,
    private val onbrightnesschanged: (LightItem, Int) -> Unit,
    private val oncolorchanged: (LightItem) -> Unit,

): RecyclerView.Adapter<LightAdapter.LightViewHolder>() {

    class LightViewHolder(itemview: View): RecyclerView.ViewHolder(itemview) {

        val lightName: TextView = itemView.findViewById(R.id.lightname)
        val lightSwitch: SwitchCompat = itemView.findViewById(R.id.switch_light)
        val brightnessSeekBar: SeekBar = itemView.findViewById(R.id.brightnessSeekBar)
        val colorPickerButton: Button = itemView.findViewById(R.id.colorpickerbutton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LightViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.light_item, parent, false)
        return LightViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lights.size
    }

    override fun onBindViewHolder(holder: LightViewHolder, position: Int) {
        val light = lights[position]
        holder.lightName.text = light.name
        holder.lightSwitch.isChecked = light.on ?: false
        holder.brightnessSeekBar.progress = light.bri ?: 0

        holder.lightSwitch.setOnCheckedChangeListener { _, isChecked ->
            onSwitchchanged(light, isChecked)
        }

        holder.brightnessSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    onbrightnesschanged(light, progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        holder.colorPickerButton.setOnClickListener {
            oncolorchanged(light)
        }
    }

    fun updateData(newLights: List<LightItem>) {
        lights.clear()
        lights.addAll(newLights)
        notifyDataSetChanged()
    }


}