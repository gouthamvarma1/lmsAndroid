package com.example.lmsandroid

import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*


class LoginActivity : AppCompatActivity() {
    var email: EditText? = null
    var password: EditText? = null
    var register: Button? = null
    var login: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        textViewRegister.setOnClickListener{
            startActivity(Intent(this@LoginActivity,MainActivity::class.java))
        }

        buttonLogin.setOnClickListener {

            val email = editTxtEmail.text.toString().trim()
            val password = editTxtPassword.text.toString().trim()



            if(email.isEmpty()){
                editTxtEmail.error = "Email required"
                editTxtEmail.requestFocus()
                return@setOnClickListener
            }

            if(!isEmail(editTxtEmail)) {
                editTxtEmail.error = "Email format is incorrect"
                editTxtEmail.requestFocus()
                return@setOnClickListener
            }
            if(password.isEmpty()){
                editTxtPassword.error = "Password required"
                editTxtPassword.requestFocus()
                return@setOnClickListener
            }

            startActivity(Intent(this@LoginActivity,CourseActivity::class.java))


        }

        }


    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter("android.intent.action.AIRPLANE_MODE")
        registerReceiver(wifiStateReceiver, intentFilter)
    }
    override fun onStop() {
        super.onStop()
        unregisterReceiver(wifiStateReceiver)
    }
    private val wifiStateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.getIntExtra(
                WifiManager.EXTRA_WIFI_STATE,
                WifiManager.WIFI_STATE_UNKNOWN
            )) {
                WifiManager.WIFI_STATE_ENABLED -> {

                    Toast.makeText(this@LoginActivity, "Wifi is On", Toast.LENGTH_SHORT).show()
                }
                WifiManager.WIFI_STATE_DISABLED -> {

                    Toast.makeText(this@LoginActivity, "Wifi is Off", Toast.LENGTH_SHORT).show()
                }
            }

            val isAirplaneModeOn = intent.getBooleanExtra("state", false);
            if(isAirplaneModeOn){

                // handle Airplane Mode on
                Toast.makeText(this@LoginActivity, "Airplane Mode is On", Toast.LENGTH_SHORT).show()
            } else {
                // handle Airplane Mode off
                Toast.makeText(this@LoginActivity, "Airplane Mode Mode is Off", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun validateEmail(): Boolean {
        var isValid = true
            if (!isEmail(email!!)) {
                email!!.setError("Invalid email id- please reenter!")
                isValid = false
            }



        if (isValid) {
            val emailValue: String = email?.getText().toString()
            val passwordValue = password!!.text.toString()
            if (emailValue == "bits@test.com" && passwordValue == "bits1234") {
                //all validations done, starting activity.
                val i = Intent(this@LoginActivity, CourseActivity::class.java)
                startActivity(i)
                //Closing this activity
                finish()
            } else {
                val t = Toast.makeText(this, "Wrong email or password!", Toast.LENGTH_LONG)
                t.show()
            }
        }

        return isValid
    }



    fun isEmail(text: EditText): Boolean {
        val email: CharSequence = text.text.toString()
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


}