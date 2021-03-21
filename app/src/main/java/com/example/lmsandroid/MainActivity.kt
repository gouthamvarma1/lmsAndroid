package com.example.lmsandroid

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.text.TextUtils
import android.util.Patterns
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import androidx.appcompat.app.AlertDialog
import android.widget.Toast
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody



class   MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewLogin.setOnClickListener{

            startActivity(Intent(this@MainActivity,LoginActivity::class.java))
        }
        buttonSignUp.setOnClickListener {

            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            val password2 = editTextPassword2.text.toString().trim()
            val fullName = editTextFullName.text.toString().trim()


            if(email.isEmpty()){
                editTextEmail.error = "Email required"
                editTextEmail.requestFocus()
                return@setOnClickListener
            }

            if(!isEmail(editTextEmail)){
                editTextEmail.error = "Email format is incorrect"
                editTextEmail.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty()){
                editTextPassword.error = "Password required"
                editTextPassword.requestFocus()
                return@setOnClickListener
            }

            if(password2.isEmpty()){
                editTextPassword2.error = "Password required again"
                editTextPassword2.requestFocus()
                return@setOnClickListener
            }

            if(fullName.isEmpty()){
                editTextFullName.error = "FullName required"
                editTextFullName.requestFocus()
                return@setOnClickListener
            }
            if(!(password.equals(password2)))
            {
                editTextPassword.error = "Password mismatch"
                editTextPassword2.text.clear()
                editTextPassword.requestFocus()
                return@setOnClickListener
            }

           val payload = "{\n" +
                     "    \"name\": \"morpheus\",\n" +
                     "    \"job\": \"leader\"\n" +
                     "}"

            val url= "https://reqres.in/api/users"

            registerUser(url,payload)


        }
    }

    fun registerUser(url:String,payload:String ) {

        val okHttpClient = OkHttpClient()
        val requestBody = payload.toRequestBody()
        val request = Request.Builder()
            .method("POST", requestBody)
            .url(url)
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@MainActivity,"failed",Toast.LENGTH_LONG)
            }

            override fun onResponse(call: Call, response: Response) {
                Looper.prepare()
                Toast.makeText(this@MainActivity,"Response code:"+response.code,Toast.LENGTH_LONG).show()

                Looper.loop()
            }
        })
    }

    fun isEmail(text: EditText): Boolean {
        val email: CharSequence = text.text.toString()
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
