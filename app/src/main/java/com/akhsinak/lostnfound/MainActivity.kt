package com.akhsinak.lostnfound

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    //
    private lateinit var auth: FirebaseAuth
//    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
//        auth = FirebaseAuth.getInstance()
//        database = FirebaseDatabase.getInstance()

////        if(auth.currentUser == null){
//            val intent = Intent(this,SignInActivity::class.java)
//            startActivity(intent)
////        }
        val user = Firebase.auth.currentUser
        if (user != null && user.isEmailVerified) {
            val intent = Intent(this, dashboard2::class.java)
            startActivity(intent)
        } else {
            // No user is signed in
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}