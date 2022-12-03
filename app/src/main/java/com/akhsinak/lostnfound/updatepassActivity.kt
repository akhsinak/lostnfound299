package com.akhsinak.lostnfound

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
//import com.google.firebase.auth.FirebaseAuth


class updatepassActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_updatepass)

        val updatepasspagebtn : Button = findViewById(R.id.updatepasspage_btn)
        val nowpass : EditText = findViewById(R.id.updatepasspage_current_password)
        val newpass : EditText = findViewById((R.id.updatepasspage_new_password))
        val confpass : EditText = findViewById((R.id.updatepasspage_confirm_password))

        auth = FirebaseAuth.getInstance()
        updatepasspagebtn.setOnClickListener {



            if (nowpass.text.isNotEmpty() &&
                newpass.text.isNotEmpty() &&
                confpass.text.isNotEmpty()
            ) {

                if (newpass.text.toString().equals(confpass.text.toString())) {

                    val user = auth.currentUser
                    if (user != null && user.email != null) {
                        val credential = EmailAuthProvider
                            .getCredential(user.email!!, nowpass.text.toString())

// Prompt the user to re-provide their sign-in credentials
                        user?.reauthenticate(credential)
                            ?.addOnCompleteListener {
                                if (it.isSuccessful) {
                                    Toast.makeText(this, "Re-Authentication success.", Toast.LENGTH_SHORT).show()
                                    user?.updatePassword(newpass.text.toString())
                                        ?.addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                Toast.makeText(this, "Password changed successfully.", Toast.LENGTH_SHORT).show()
                                                auth.signOut()
                                                startActivity(Intent(this, SignInActivity::class.java))
                                                finish()
                                            }
                                        }

                                } else {
                                    Toast.makeText(this, "Re-Authentication failed.", Toast.LENGTH_SHORT).show()
                                }
                            }
                    } else {
                        startActivity(Intent(this, SignInActivity::class.java))
                        finish()
                    }

                } else {
                    Toast.makeText(this, "Password mismatching.", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "Please enter all the fields.", Toast.LENGTH_SHORT).show()
            }

        }
    }





}