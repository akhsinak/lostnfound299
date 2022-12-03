package com.akhsinak.lostnfound

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgotPassActivity : AppCompatActivity() {
    private lateinit var etpass:EditText
    private lateinit var btn_reset: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pass)



        etpass = findViewById(R.id.forgotpw_email_textinput)
        btn_reset = findViewById(R.id.resetpw_button)

        auth = FirebaseAuth.getInstance()


        btn_reset.setOnClickListener{

            val spass = etpass.text.toString()
            auth.sendPasswordResetEmail(spass)
                .addOnSuccessListener {
                    Toast.makeText(this,"Reset Link Sent To Email",Toast.LENGTH_SHORT).show()

                }
                .addOnFailureListener{

                    Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
                }



        }
    }
}