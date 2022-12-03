package com.akhsinak.lostnfound

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.akhsinak.lostnfound.databinding.ActivityPostLostItemBinding
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_post_lost_item.*

class post_lost_item_activity : AppCompatActivity() {

    private lateinit var binding: ActivityPostLostItemBinding

    private var lost1Uri : Uri? = null
    private val selectimg = registerForActivityResult(ActivityResultContracts.GetContent()){

        lost1Uri = it

        binding.lostimg1.setImageURI(lost1Uri)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostLostItemBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val lostname : EditText = findViewById(R.id.lost_name_textinput)
        val lostphone : EditText = findViewById(R.id.lost_phone_textinput)
        val lostwhere : EditText = findViewById(R.id.lost_where_textinput)
        val lostdes : EditText = findViewById(R.id.lost_des_textinput)

        binding.lostimg1.setOnClickListener{

            selectimg.launch("image/*")

        }

        binding.lostUpload.setOnClickListener{

            val name1  = lostname.text.toString()
            val phone1  = lostphone.text.toString()
            val where1  = lostwhere.text.toString()
            val des1  = lostdes.text.toString()

            postlostProgressbar.visibility = View.VISIBLE

                if((name1.isEmpty()) || phone1.isEmpty() || where1.isEmpty() || des1.isEmpty() || lost1Uri == null){
                    if(name1.isEmpty())
                    {
                        lostname.error = "Enter Your Name"
                    }
                    if(phone1.isEmpty())
                    {
                        lostphone.error = "Enter Your Email"
                    }
                    if(where1.isEmpty())
                    {
                        lostwhere.error = "Enter Your Phone"
                    }
                    if(des1.isEmpty())
                    {
                        lostdes.error = "Enter Your Password"
                    }
                    Toast.makeText(this,"Please fill all fields properly", Toast.LENGTH_SHORT).show()
                    postlostProgressbar.visibility = View.GONE
                }

                else if(phone1.length != 10){
                    postlostProgressbar.visibility = View.GONE
                    lostphone.error = "Enter Valid Phone Number"
                    Toast.makeText(this,"Enter Valid Phone Number",Toast.LENGTH_SHORT).show()
                }

                else{

                    //calls the function now
                    uploadImage()
                }


        }



    }

    private fun uploadImage() {

        postlostProgressbar.visibility = View.VISIBLE

        val storageRef = FirebaseStorage.getInstance().getReference("lostimages")
//            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child("lost1.jpg")


        storageRef.putFile(lost1Uri!!)
            .addOnSuccessListener {

                storageRef.downloadUrl.addOnSuccessListener {



                        //another method here-------------------------------------
                        storeData(it)



                }.addOnFailureListener{
                        Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
                    }

            }
            .addOnFailureListener{
                Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
            }




    }

    private fun storeData(imageUrl: Uri?) {

        val data = LostModel(
            lostname = binding.lostNameTextinput.text.toString(),
            lostphone = binding.lostPhoneTextinput.text.toString(),
            lostplace = binding.lostWhereTextinput.text.toString(),
            lostdes = binding.lostDesTextinput.text.toString(),



        )

        FirebaseDatabase.getInstance().getReference("lostdata")
            .child(FirebaseAuth.getInstance().currentUser!!.phoneNumber!!)
            .setValue(data).addOnCompleteListener{

                if(it.isSuccessful){

                    startActivity(Intent(this,dashboard2::class.java))
                    finish()

                    Toast.makeText(this,"Data Upload Successful", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(this,"something went wrong", Toast.LENGTH_SHORT).show()
                }
            }

    }


}