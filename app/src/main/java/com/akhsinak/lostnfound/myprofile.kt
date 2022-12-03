package com.akhsinak.lostnfound

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class myprofile : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    //    private lateinit var text: TextView
    private lateinit var update_pw: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myprofile)




//variables connecting to the my profile xml file--------------------------------------
        var name : TextView = findViewById(R.id.dashname)
        var email : TextView = findViewById(R.id.dashemail)
        var emailverified : TextView = findViewById(R.id.dashverified)
        var uid : TextView = findViewById(R.id.dashuid)
//--------------------------------------------------------------------





        //code from google firebase for extracting the user meta information from the firebase metadaata----------
        val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
//            name.text = user.displayName.toString()
            email.text = user.email.toString()
//            val photoUrl = user.photoUrl

            // Check if user's email is verified
            emailverified.text = user.isEmailVerified.toString()

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            uid.text = user.uid.toString()
        }
        //-------------------------------------------------------------------------------

        update_pw = findViewById(R.id.myprofile_updatepw)

        update_pw.setOnClickListener {
            val intent  = Intent(this,updatepassActivity::class.java)
            startActivity(intent)
        }



    }
}