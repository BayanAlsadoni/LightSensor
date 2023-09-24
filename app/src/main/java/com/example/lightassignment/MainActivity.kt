package com.example.lightassignment

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity(),SensorEventListener {

    lateinit var sensorManager:SensorManager
    lateinit var sensor:Sensor
    lateinit var textView:TextView

//    private lateinit var cameraManager:CameraManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager =getSystemService(SENSOR_SERVICE) as SensorManager

//        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

        textView = findViewById(R.id.textView)

        var sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        if (sensorLight != null){
            sensor = sensorLight
        }else{
            Toast.makeText(this, "sensor not found", Toast.LENGTH_SHORT).show()
            Log.e("byn","sensor not found")
        }

//        sensorManager.registerListener(this, sensor,SensorManager.SENSOR_DELAY_NORMAL)


        val i = Intent(this, MyService::class.java) //maybe do it in start
        ContextCompat.startForegroundService(this, i)


        sensorManager.registerListener(this, sensor,SensorManager.SENSOR_DELAY_NORMAL)


    }

//    override fun onStart() {
//        super.onStart()
//        sensorManager.registerListener(this, sensor,SensorManager.SENSOR_DELAY_NORMAL)
//
//        val i = Intent(this, MyService::class.java) //maybe do it in start
//        ContextCompat.startForegroundService(this, i)
//
//    }

//    override fun onResume() {
//        super.onResume()
//        sensorManager.registerListener(this, sensor,SensorManager.SENSOR_DELAY_NORMAL)
//
////        val i = Intent(this, MyService::class.java) //maybe do it in start
////        ContextCompat.startForegroundService(this, i)
//    }
//
//    override fun onPause() {
//        super.onPause()
//        sensorManager.unregisterListener(this)
////        val i = Intent(this, MyService::class.java)
////        stopService(i)
//    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this)
        val i = Intent(this, MyService::class.java)
        stopService(i)

    }

//    override fun onStop() {
//        super.onStop()
//        sensorManager.unregisterListener(this)
//        val i = Intent(this, MyService::class.java)
//        stopService(i)
//
//    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0!!.sensor.type == Sensor.TYPE_LIGHT){


            var lightValue =p0.values[0].toInt()
            var str = "value: $lightValue \nMax value: ${p0.sensor.maximumRange}"
            textView.text = str

//
//            if (lightValue < 50){
//                var cameralistid = cameraManager.cameraIdList[0]
//
//                cameraManager.setTorchMode(cameralistid,true)
//
//            }else{
//                var cameralistid = cameraManager.cameraIdList[0]
//                cameraManager.setTorchMode(cameralistid,false)
//            }

            val lig = Intent(this,MyService::class.java)
            lig.putExtra("lightvalue",lightValue)
             startService(lig)


//            if (lightValue < 50){
//
//                val i = Intent(this, MyService::class.java) //maybe do it in start
//                ContextCompat.startForegroundService(this, i)
//
//            }else{
//
//                val i = Intent(this, MyService::class.java)
//                stopService(i)
//            }


        }

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}