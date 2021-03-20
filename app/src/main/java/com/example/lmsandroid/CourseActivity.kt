package com.example.lmsandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.nfc.Tag
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.math.log

val TAG = "CourseActivity"

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
        values.put(CourseProvider.NAME, (findViewById<View>(R.id.courseName) as EditText).text.toString())


        // inserting into database through content URI
        contentResolver.insert(CourseProvider.CONTENT_URI, values)

        // displaying a toast message
        Toast.makeText(baseContext, "New Record Inserted", Toast.LENGTH_LONG).show()
    }

    fun onClickShowDetails(view: View?) {
        val TAG = "CourseActivity"

        Log.i(TAG, "In Course Activity")
        // inserting complete table details in this text field
        val resultView = findViewById<View>(R.id.res) as TextView

        // creating a cursor object of the
        // content URI
        val cursor = contentResolver.query(Uri.parse("content://com.example.lmsandroid.CourseProvider/course"), null, null, null, null)

        // iteration of the cursor
        // to print whole table
        if (cursor!!.moveToFirst()) {
            val strBuild = StringBuilder()
            while (!cursor.isAfterLast) {
                strBuild.append(""" 
      
    ${cursor.getString(cursor.getColumnIndex("_id"))}-${cursor.getString(cursor.getColumnIndex("name"))} 
    """.trimIndent())
                cursor.moveToNext()
            }
            resultView.text = strBuild
        } else {
            resultView.text = "No Records Found"
        }
    }

}