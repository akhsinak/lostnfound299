package com.akhsinak.lostnfound

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text

class SignInActivity : AppCompatActivity() {

        private lateinit var auth : FirebaseAuth
        private lateinit var database : FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

//        val signUpName : EditText = findViewById(R.id.sign)
        val signin_signup_text : TextView = findViewById(R.id.signin_signup)

        signin_signup_text.setOnClickListener {
            val intent  = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}