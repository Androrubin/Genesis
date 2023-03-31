package com.androrubin.genesis.login_and_splash

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.androrubin.genesis.MainActivity
import com.androrubin.genesis.R
import com.androrubin.genesis.databinding.ActivityCreateProfileBinding
import com.androrubin.genesis.ui.home.HomeFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

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
       // lateinit var week_count_text:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityCreateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gender_text ="female"

       // week_count_text=binding.weekCount.text.toString()




        db= FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        val name = currentUser?.displayName
        val uid = currentUser?.uid

        val edtName = findViewById<EditText>(R.id.edtName)
        val edtAge = findViewById<EditText>(R.id.edtAge)
        val  edtWeek = findViewById<EditText>(R.id.week_count)


        val btnSave = findViewById<Button>(R.id.btnSave)





        btnSave.setOnClickListener {

            if (TextUtils.isEmpty(edtName.getText()?.trim().toString())) {
                edtName.setError("Field cannot be empty")
                edtName.requestFocus()
            }else  if (TextUtils.isEmpty(edtAge.getText()?.trim().toString())) {
                edtAge.setError("Field cannot be empty")
                edtAge.requestFocus()
            }
            else  if (TextUtils.isEmpty(edtWeek.getText()?.trim().toString())) {
                edtAge.setError("Field cannot be empty")
                edtAge.requestFocus()
            }
            else{

                val data= hashMapOf(

                    "Name" to edtName.text.trim().toString(),
                    "Age" to edtAge.text.trim().toString(),
                    "Gender" to gender_text.trim(),
                    "Pregnancy Week" to edtWeek.text.trim().toString() ,
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

//                val bundle = Bundle()
//                bundle.putString("weekCount", week_count_text)
//
//                val fragobj = HomeFragment()
//                fragobj.setArguments(bundle)

                val intent= Intent(this, MainActivity::class.java)
           //     intent.putExtra("week_count",binding.weekCount.text.toString())
                startActivity(intent)
                finish()
            }
        }


    }


}