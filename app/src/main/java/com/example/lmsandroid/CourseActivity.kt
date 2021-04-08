package com.example.lmsandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.nfc.Tag
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_course.*
import kotlin.math.log

val TAG = "CourseActivity"

val URL = "https://768ff066e77d.ngrok.io/api/course/courses/"

class CourseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course)
    }
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        return true
    }

    fun onClickAddDetails(view: View?) {

        // class to add values in the database
        val values = ContentValues()


        // fetching text from user
        values.put(
            CourseProvider.NAME,
            (findViewById<View>(R.id.courseName) as EditText).text.toString()
        )


        // inserting into database through content URI
        contentResolver.insert(CourseProvider.CONTENT_URI, values)

        // displaying a toast message
        Toast.makeText(baseContext, "New Record Inserted", Toast.LENGTH_LONG).show()
    }

    fun onClickShowDetails(view: View?) {
        val TAG = "CourseActivity"

        Log.i(TAG, "In Course Activity")
        // inserting complete table details in this text field
        val resultView = result

        // creating a cursor object of the
        // content URI
        val cursor = contentResolver.query(
            Uri.parse("content://com.example.lmsandroid.CourseProvider/course"),
            null,
            null,
            null,
            "_id ASC"
        )

        // iteration of the cursor
        // to print whole table
        if (cursor!!.moveToFirst()) {
            val strBuild = StringBuilder()
            while (!cursor.isAfterLast) {
                strBuild.append(
                    """
      
    ${cursor.getString(cursor.getColumnIndex("_id"))}-${cursor.getString(cursor.getColumnIndex("name"))} 
    """.trimIndent()
                )

                    // post that in the API service  http://192.168.0.105/api/course/courses/
                    var serviceIntent = Intent(this, CourseContentService::class.java)

                    serviceIntent.putExtra("COURSE_URL", URL)
                    serviceIntent.putExtra(
                        "COURSE_NAME",
                        """${cursor.getString(cursor.getColumnIndex("name"))}"""
                    )
                    serviceIntent.putExtra("COURSE_CREDIT", 10)
                    startService(serviceIntent);
                    // call service function for posting in api
                    Log.d("service started","strted")

                cursor.moveToNext()

            }
            resultView.text = strBuild
        } else {
            resultView.text = "No Records Found"
        }

        // service
        // push courses in an api     http://192.168.0.105/api/course/courses/


//        setContentView(R.layout.activity_button_example)
//        val constraintLayout = findViewById(R.id.constraintLayout) as ConstraintLayout
//        val button = Button(this)
//        button.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
//        button.text = "Click me"
//        button.setOnClickListener(View.OnClickListener {
//            button.text = "You just clicked me"
//        })
//        button.setBackgroundColor(Color.GREEN)
//        button.setTextColor(Color.RED)
//        constraintLayout.addView(button);
    }

}