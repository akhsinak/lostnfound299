package com.akhsinak.lostnfound

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text

class SignInActivity : AppCompatActivity() {


        private lateinit var auth : FirebaseAuth
//        private lateinit var database : FirebaseDatabase
        private val emailPattern = "[a-zA-Z0-9._-]+@iitp.ac.in"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = FirebaseAuth.getInstance()
//        database = FirebaseDatabase.getInstance()

        val signinemail : EditText = findViewById(R.id.signin_email_textinput)
        val signinpass : EditText = findViewById((R.id.signin_pass_textinput))
        val signinpasslayout : TextInputLayout = findViewById((R.id.signin_pass_layout))
        val signinbtn : Button = findViewById(R.id.SignInButton)
        val signinprobar: ProgressBar = findViewById(R.id.SignInProgressBar)

        val signin_signup_text : TextView = findViewById(R.id.signin_signup)

        signin_signup_text.setOnClickListener {
            val intent  = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }

        signinbtn.setOnClickListener{
            signinprobar.visibility = View.VISIBLE
            signinpasslayout.isPasswordVisibilityToggleEnabled= true

            val email_str = signinemail.text.toString()
            val pass_str = signinpass.text.toString()


            if(email_str.isEmpty() || pass_str.isEmpty())
            {
                if(email_str.isEmpty())
                {
                    signinemail.error = "Enter The Email"

                }
                if(pass_str.isEmpty())
                {
                    signinpass.error = "Enter Your Password"
                    signinpasslayout.isPasswordVisibilityToggleEnabled = false

                }
                signinprobar.visibility = View.GONE
                Toast.makeText(this,"Enter Valid Details",Toast.LENGTH_SHORT).show()

            }
            else if(!email_str.matches(emailPattern.toRegex())){
                signinprobar.visibility = View.GONE
                signinemail.error = "Enter Valid Email Address"
                Toast.makeText(this,"Enter Valid Email Address",Toast.LENGTH_SHORT).show()
            }
            else if (pass_str.length < 8)
            {
                signinpasslayout.isPasswordVisibilityToggleEnabled = true
                signinprobar.visibility = View.GONE
                signinpass.error = "Password length must be atleast 8 chars"
                Toast.makeText(this,"Enter password with length atleast 8",Toast.LENGTH_SHORT).show()
            }
            else
            {
                auth.signInWithEmailAndPassword(email_str,pass_str).addOnCompleteListener{
                    if(it.isSuccessful){
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                    }
                    else
                    {
                        Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_SHORT).show()
                        signinprobar.visibility = View.GONE
                    }
                }
            }
         }
    }
}