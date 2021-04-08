package com.example.lmsandroid

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Looper
import android.widget.Toast
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class CourseContentService : Service() {

    override fun onBind(p0: Intent?): IBinder? {
        return null;
    }

    fun SaveCourseContent(url:String,payload:String ) {

        val okHttpClient = OkHttpClient()
        val requestBody = payload.toRequestBody()
        val request = Request.Builder()
            .method("POST", requestBody)
            .url(url)
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@MainActivity,"failed", Toast.LENGTH_LONG)
            }

            override fun onResponse(call: Call, response: Response) {
                Looper.prepare()
                Toast.makeText(this@MainActivity,"Service call Successful", Toast.LENGTH_LONG).show()
                Looper.loop()
            }
        })
    }

}