package com.example.lightassignment

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.lightassignment.MyApp.Companion.CHANNEL_ID

class MyService : Service() {

    lateinit var cameraManager: CameraManager


    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        super.onCreate()
        Toast.makeText(this, "service is created", Toast.LENGTH_SHORT).show()
        Log.e("byn","create service")
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "service is started", Toast.LENGTH_SHORT).show()
        Log.e("byn","start service")


        val notificationIntern= Intent(this, MainActivity::class.java)
        val pindingIntent = PendingIntent.getActivity(this, 0, notificationIntern, 0)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Flashlight playing")
            .setContentText("flashlight is playing in background")
            .setContentIntent(pindingIntent)
            .setSmallIcon(R.drawable.ic_baseline_flash_on_24)
            .setAutoCancel(true)
            .build()



        startForeground(1,notification)


        var lightValue= intent!!.getIntExtra("lightvalue", 0)

        if (lightValue < 50){
            var cameralistid = cameraManager.cameraIdList[0]

            cameraManager.setTorchMode(cameralistid,true)

        }else{
            var cameralistid = cameraManager.cameraIdList[0]
            cameraManager.setTorchMode(cameralistid,false)
        }
//
//        var cameralistid = cameraManager.cameraIdList[0]
////
//            cameraManager.setTorchMode(cameralistid,true)

        return START_REDELIVER_INTENT
//        return START_NOT_STICKY

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "service is destroyed", Toast.LENGTH_SHORT).show()
        Log.e("byn","destroy service")


//        var cameralistid = cameraManager.cameraIdList[0]
//            cameraManager.setTorchMode(cameralistid,false)
    }


}