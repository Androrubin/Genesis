package com.apoorv.runanddetect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call.Details
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class YogaDesc : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yoga_desc)
        val btn=findViewById<Button>(R.id.button)
        btn.setOnClickListener{
            val intent= Intent(this, TimerActivity::class.java)
            startActivity(intent)
        }
        val btn2=findViewById<Button>(R.id.buttoncam)
        btn2.setOnClickListener{
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val Yoganame=intent.getStringExtra("name")
        val YogaDetails=intent.getStringExtra("Detail")
        val YogaImage=intent.getIntExtra("ImageId", R.drawable.a)
        val yogaName=findViewById<TextView>(R.id.text_yoga)
        val yogaDetail=findViewById<TextView>(R.id.yoga_details)
        val yogaImage=findViewById<ImageView>(R.id.yoga_img)
        yogaName.text=Yoganame
        yogaDetail.text=YogaDetails
        yogaImage.setImageResource(YogaImage)
    }
}