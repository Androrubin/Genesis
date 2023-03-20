package com.androrubin.genesis.login_and_splash

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.androrubin.genesis.MainActivity
import com.androrubin.genesis.R
import com.androrubin.genesis.ui.home.HomeFragment
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser

        if (user != null) {

            Handler().postDelayed({

                val user = mAuth.currentUser
                val name = user?.uid

                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)


            }, 1500)
        }else {
            Handler().postDelayed({

                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)


            }, 3500)

        }
    }

//    private fun onBoardingFinished(): Boolean {
//        val sharedPref = applicationContext.getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
//        return sharedPref.getBoolean("Finished",false)
//
//
//
//    }
}