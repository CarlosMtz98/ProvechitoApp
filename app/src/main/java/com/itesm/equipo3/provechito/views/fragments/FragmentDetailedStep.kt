package com.itesm.equipo3.provechito.views.fragments

import android.content.Context
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import androidx.fragment.app.Fragment
import com.itesm.equipo3.provechito.views.listeners.HomeClickListener
import com.itesm.equipo3.provechito.databinding.FragmentDetailedStepBinding
import kotlin.properties.Delegates

class FragmentDetailedStep : Fragment() {
    private var _binding: FragmentDetailedStepBinding? =  null
    private val binding get() = _binding!!
    private lateinit var listener: HomeClickListener
    private lateinit var chronometer: Chronometer
    private var running = false
    private var pauseOffSet by Delegates.notNull<Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeClickListener) {
            listener = context
        } else {
            throw ClassCastException(context.toString() + " must implement HomeClickListner.")
        }
    }

    override fun onCreateView(
            iinflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailedStepBinding.inflate(layoutInflater, container, false)
        binding.btnNextStep.setOnClickListener {
            val reviewFragment = ReviewFragment()
            listener.onNextClicked()
        }
        pauseOffSet = 0
        chronometer = binding.chronometer
        chronometer.text = "00:00:00"
        chronometer.setOnChronometerTickListener{
            val time = SystemClock.elapsedRealtime() - chronometer.base
            val h = (time / 3600000).toInt()
            val m = (time - h * 3600000).toInt() / 60000
            val s = (time - h * 3600000 - m * 60000).toInt() / 1000
            val t = (if (h < 10) "0$h" else h).toString() + ":" + (if (m < 10) "0$m" else m) + ":" + if (s < 10) "0$s" else s
            chronometer.text = t

        }

        binding.imgButtonStart.setOnClickListener {
            startChronometer()
        }
        binding.imgButtonPause.setOnClickListener {
            pauseChronometer()
        }
        binding.imgButtonReset.setOnClickListener {
            resetChronometer()
        }
        return binding.root
    }

    private fun startChronometer(){
        if(!running){
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffSet)
            chronometer.start()
            running = true
        }
    }

    private fun pauseChronometer(){
        if(running){
            chronometer.stop()
            pauseOffSet = SystemClock.elapsedRealtime() - chronometer.getBase()
            running = false
        }
    }

    private fun resetChronometer(){
        chronometer.setBase(SystemClock.elapsedRealtime())
        pauseOffSet = 0
        chronometer.text = "00:00:00"
    }

}