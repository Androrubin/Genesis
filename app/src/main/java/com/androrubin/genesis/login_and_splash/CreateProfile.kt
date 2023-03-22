package com.androrubin.genesis.login_and_splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import com.androrubin.genesis.MainActivity
import com.androrubin.genesis.R
import com.androrubin.genesis.databinding.ActivityCreateProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_create_profile.*

class CreateProfile : AppCompatActivity() {

    override fun onResume(){

        super.onResume()
        val stages= resources.getStringArray(R.array.child_age)
        val arrayAdapter = ArrayAdapter(this,R.layout.dropdown_layout,stages)
        binding.yearText.setAdapter(arrayAdapter)

    }

        private lateinit var binding:ActivityCreateProfileBinding
        private lateinit var db:FirebaseFirestore
        private lateinit var mAuth: FirebaseAuth
        lateinit var gender_text:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityCreateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gender_text ="female"



        binding.genderBtnGrp.setOnCheckedChangeListener{ group, checkedId->

            if(checkedId==R.id.male) {
                gender_text = male.text.toString()

            }
            else if(checkedId==R.id.female)
                gender_text=female.text.toString()

            else
                gender_text="Prefer not to say"

            Toast.makeText(this,"Gender=$gender_text",Toast.LENGTH_SHORT).show()
        }



        db= FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        val name = currentUser?.displayName
        val uid = currentUser?.uid

        val edtName = findViewById<EditText>(R.id.edtName)
        val edtAge = findViewById<EditText>(R.id.edtAge)


        val btnSave = findViewById<Button>(R.id.btnSave)



        btnSave.setOnClickListener {

            if (TextUtils.isEmpty(edtName.getText()?.trim().toString())) {
                edtName.setError("Field cannot be empty")
                edtName.requestFocus()
            }else  if (TextUtils.isEmpty(edtAge.getText()?.trim().toString())) {
                edtAge.setError("Field cannot be empty")
                edtAge.requestFocus()
            }
            else{

                val data= hashMapOf(

                    "Name" to edtName.text.trim().toString(),
                    "Age" to edtAge.text.trim().toString(),
                    "Pregnancy Week " to  gender_text.trim(),
                    "Child's Age" to binding.yearText.text.trim().toString(),
                    "ProfileCreated" to "1"

                )
                db.collection("Users").document("$uid")
                    .set(data)
                    .addOnSuccessListener {docRef ->
                        Log.d("Data Addition", "DocumentSnapshot written with ID: ${docRef}.id")
                        Toast.makeText(this,"Saved", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Log.w("Data Addition", "Error adding document", e)
                    }

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }


    }
}