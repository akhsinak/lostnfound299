package com.akhsinak.lostnfound

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var database : FirebaseDatabase
    private val emailPattern = "[a-zA-Z0-9._-]+@iitp.ac.in"

//        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth  = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        val signUpName : EditText = findViewById(R.id.signup_name_textinput)
        val signUpEmail : EditText = findViewById(R.id.signup_email_textinput)
        val signUpPhone : EditText = findViewById(R.id.signup_phone_textinput)
        val signUpPass : EditText = findViewById(R.id.signup_pass_textinput)
        val signUpCPass : EditText = findViewById(R.id.signup_cpass_textinput)
        val signUpPassLayout : TextInputLayout = findViewById(R.id.signup_pass_layout)
        val signUpCPassLayout : TextInputLayout = findViewById(R.id.signup_cpass_layout)
        val signUpBtn: Button = findViewById(R.id.SignUpButton)
        val signUpProgressBar : ProgressBar = findViewById(R.id.signupProgressbar)

        val signupsignintext : TextView = findViewById(R.id.signup_signin)

        signupsignintext.setOnClickListener {
            val intent  = Intent(this,SignInActivity::class.java)
            startActivity(intent)
        }

                signUpBtn.setOnClickListener {
                    val name  = signUpName.text.toString()
                    val email  = signUpEmail.text.toString()
                    val phone  = signUpPhone.text.toString()
                    val pass  = signUpPass.text.toString()
                    val cpass  = signUpCPass.text.toString()

                    signUpProgressBar.visibility = View.VISIBLE
                    signUpPassLayout.isPasswordVisibilityToggleEnabled = true
                    signUpCPassLayout.isPasswordVisibilityToggleEnabled = true

                    if((name.isEmpty()) || email.isEmpty() || phone.isEmpty() || pass.isEmpty() || cpass.isEmpty()){
                        if(name.isEmpty())
                        {
                            signUpName.error = "Enter Your Name"
                        }
                        if(email.isEmpty())
                        {
                            signUpEmail.error = "Enter Your Email"
                        }
                        if(phone.isEmpty())
                        {
                            signUpPhone.error = "Enter Your Phone"
                        }
                        if(pass.isEmpty())
                        {
                            signUpPassLayout.isPasswordVisibilityToggleEnabled = false
                            signUpPass.error = "Enter Your Password"
                        }
                        if(cpass.isEmpty())
                        {
                            signUpCPassLayout.isPasswordVisibilityToggleEnabled = false
                            signUpCPass.error = "Confirm Your Password"
                        }

                        Toast.makeText(this,"Enter valid Details",Toast.LENGTH_SHORT).show()
                        signUpProgressBar.visibility = View.GONE
                    }

//                    else if(!email.matches(emailPattern.toRegex())){
//                        signUpProgressBar.visibility = View.GONE
//                        signUpEmail.error = "Enter Valid Email Address"
//                        Toast.makeText(this,"Enter Valid Email Address",Toast.LENGTH_SHORT).show()
//                    }
                    else if(phone.length != 10){
                        signUpProgressBar.visibility = View.GONE
                        signUpPhone.error = "Enter Valid Phone Number"
                        Toast.makeText(this,"Enter Valid Phone Number",Toast.LENGTH_SHORT).show()
                    }
                    else if (pass.length < 8)
                    {
                        signUpPassLayout.isPasswordVisibilityToggleEnabled = true
                        signUpProgressBar.visibility = View.GONE
                        signUpPass.error = "Password length must be atleast 8 chars"
                        Toast.makeText(this,"Enter password with length atleast 8",Toast.LENGTH_SHORT).show()
                    }
                    else if(cpass != pass)
                    {
                        signUpCPassLayout.isPasswordVisibilityToggleEnabled = true
                        signUpProgressBar.visibility = View.GONE
                        signUpCPass.error = "Passwords Dont Match"
                        Toast.makeText(this,"Password Dont Match",Toast.LENGTH_SHORT).show()
                    }
                    else
                    {



                        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener {
                            if(it.isSuccessful)
                            {

                                auth.currentUser?.sendEmailVerification()
                                    ?.addOnSuccessListener {
                                        Toast.makeText(this, "Please verify your email !",Toast.LENGTH_SHORT).show()

                                        //here is the data storage code

                                        val databaseRef = database.reference.child("users").child(auth.currentUser!!.uid)
                                        //                        val users : Users_Data_Class = Users_Data_Class(name, email, phone, auth.currentUser!!.uid)
                                        //                        val users : Users_Data_Class = Users_Data_Class(name, email, phone, pass, auth.currentUser!!.uid)
                                        val users : Users_Data_Class = Users_Data_Class(name, email, pass, phone, auth.currentUser!!.uid)

                                        databaseRef.setValue(users).addOnCompleteListener {
                                            if(it.isSuccessful){
                                            val intent = Intent(this,SignInActivity::class.java)
                                            startActivity(intent)

                                            }
                                            else
                                            {
                                                Toast.makeText(this,"Something Went Wrong 1",Toast.LENGTH_SHORT).show()
                                                signUpProgressBar.visibility = View.GONE
                                            }
                                        }



                                    }
                                    ?.addOnFailureListener{
                                        Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
                                    }
                            }
                            else{
                                Toast.makeText(this,"Something Went Wrong", Toast.LENGTH_SHORT).show()
                                signUpProgressBar.visibility = View.GONE
                            }
                        }
                    }
                }
    }
}



//just some random lines to check for the restore capabilities of github