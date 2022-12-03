package com.akhsinak.lostnfound

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class dashboard2 : AppCompatActivity() {

    private lateinit var logout2: Button
    private lateinit var my_profile: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard2)




        logout2 = findViewById(R.id.logout_button)

        logout2.setOnClickListener{

            Firebase.auth.signOut()

            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)

            Toast.makeText(this,"Logout Successful", Toast.LENGTH_SHORT).show()
        }

        my_profile = findViewById(R.id.profile)

        my_profile.setOnClickListener{

            val intent  = Intent(this,myprofile::class.java)
            startActivity(intent)


        }

    }
}