package com.example.lmsandroid

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {
    var email: EditText? = null
    var password: EditText? = null
    var register: Button? = null
    var login: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setupUI();
        setupListeners();
    }



    private fun setupUI() {
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        register = findViewById(R.id.register)
        login = findViewById(R.id.login)
    }

    private fun setupListeners() {
        login!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (validateEmail())
                    Toast.makeText(this@LoginActivity,"login clicked",Toast.LENGTH_LONG).show();

            }
        })
        register!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                //Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
                //startActivity(i);
            }
        })
    }


    fun validateEmail(): Boolean {
        var isValid = true
        if (email?.let { isEmpty(it) }!!) {
            email!!.setError("Email required")
            isValid = false
        } else {
            if (!isEmail(email!!)) {
                email!!.setError("Invalid email id- please reenter!")
                isValid = false
            }
        }
        if (password?.let { isEmpty(it) }!!) {
            password!!.error = "Password required"
            isValid = false
        }

        if (isValid) {
            val emailValue: String = email?.getText().toString()
            val passwordValue = password!!.text.toString()
            if (emailValue == "bits@test.com" && passwordValue == "bits1234") {
                //all validations done, starting activity.
                val i = Intent(this@LoginActivity, LoginActivity::class.java)
                startActivity(i)
                //Closing this activity
                finish()
            } else {
                val t = Toast.makeText(this, "Wrong email or password!", Toast.LENGTH_SHORT)
                t.show()
            }
        }

        return isValid
    }



    fun isEmail(text: EditText): Boolean {
        val email: CharSequence = text.text.toString()
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isEmpty(text: EditText): Boolean {
        val str: CharSequence = text.text.toString()
        return TextUtils.isEmpty(str)
    }
}