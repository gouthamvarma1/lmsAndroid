package com.example.lmsandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
/*import simplifiedcoding.net.kotlinretrofittutorial.R
import simplifiedcoding.net.kotlinretrofittutorial.api.RetrofitClient
import simplifiedcoding.net.kotlinretrofittutorial.models.DefaultResponse*/


class   MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
            if(!(password.chars().equals(password2.chars()))s)
            {
                editTextPassword.error = "Password mismatch"
                editTextPassword2.text.clear()
                editTextPassword.requestFocus()
                return@setOnClickListener
            }

s
           /* RetrofitClient.instance.createUser(email, name, password, school)
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