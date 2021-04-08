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
    init {

    }

    override fun onBind(p0: Intent?): IBinder? {
        return null;
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val _name = intent?.getStringExtra("COURSE_NAME")
        val _credit = intent?.getIntExtra("COURSE_CREDIT",1)
        val url = intent?.getStringExtra("COURSE_URL")
        val course : Course = Course(_name.toString(), _credit!!)

        SaveCourseContent(url.toString(),course )
        return super.onStartCommand(intent, flags, startId)
    }

    fun SaveCourseContent(url: String, payload: Course) {

        val okHttpClient = OkHttpClient()
        val requestBody = payload.toString().toRequestBody()
        val request = Request.Builder()
            .method("POST", requestBody)
            .url(url)
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@CourseContentService, "failed", Toast.LENGTH_LONG)
            }

            override fun onResponse(call: Call, response: Response) {
                Looper.prepare()
                Toast.makeText(this@CourseContentService, "Service call Successful", Toast.LENGTH_LONG)
                    .show()
                Looper.loop()
            }
        })
    }

}
data class Course (
    val name : String,
    val credits : Int
)