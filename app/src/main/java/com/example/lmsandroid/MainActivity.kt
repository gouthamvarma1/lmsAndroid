package com.example.lmsandroid

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import androidx.appcompat.app.AlertDialog
import android.widget.Toast
import com.example.lmsandroid.api.RetrofitClient
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody



/*import simplifiedcoding.net.kotlinretrofittutorial.R
import simplifiedcoding.net.kotlinretrofittutorial.api.RetrofitClient
import simplifiedcoding.net.kotlinretrofittutorial.models.DefaultResponse*/



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


//++++++++++++++++++++++++++++++++++++++++++++++++++++++

            val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
            alertDialog.setTitle("AlertDialog")
            alertDialog.setMessage("POST Failed")
            alertDialog.setPositiveButton(
                "yes"
            ) { _, _ ->
                Toast.makeText(this@MainActivity, "Alert dialog closed.", Toast.LENGTH_LONG).show()
            }
            alertDialog.setNegativeButton(
                "No"
            ) { _, _ -> }
            val alert: AlertDialog = alertDialog.create()
            alert.setCanceledOnTouchOutside(false)
            alert.show()

           val payload = "{\n" +
                     "    \"name\": \"morpheus\",\n" +
                     "    \"job\": \"leader\"\n" +
                     "}"

             val okHttpClient = OkHttpClient()
             val requestBody = payload.toRequestBody()
             val request = Request.Builder()
                .method("POST", requestBody)
                .url("https://reqres.in/api/users")
                .build()
                 okHttpClient.newCall(request).enqueue(object : Callback {
                 override fun onFailure(call: Call, e: IOException) {
                     Toast.makeText(this@MainActivity,"failed",Toast.LENGTH_LONG)
                 }

                 override fun onResponse(call: Call, response: Response) {
                     Toast.makeText(this@MainActivity,"success",Toast.LENGTH_LONG)
                 }
             })


          /*  RetrofitClient.instance.createUser(email, password, password2, fullName)
                .enqueue(object: Callback<DefaultResponse>{
                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                        Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()
                    }

                })*/

        }
    }
}