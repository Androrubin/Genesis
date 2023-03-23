package com.androrubin.genesis.ui.aid.book_appointment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androrubin.genesis.R


class BookAppointmentActivity : AppCompatActivity() {
    lateinit var doctorNameList: Array<String>
    lateinit var yoeList: Array<String>
    lateinit var specializationList: Array<String>
    lateinit var doctors : ArrayList<AvailableDoctorsDC>
    private lateinit var availableDoctorsListRV : RecyclerView
    lateinit var sheet : FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.androrubin.genesis.R.layout.activity_book_appointment)

        availableDoctorsListRV = findViewById(R.id.availableDoctorsRV)
        availableDoctorsListRV.layoutManager = LinearLayoutManager(this)
        availableDoctorsListRV.setHasFixedSize(true)


        doctorNameList = arrayOf(
            "Dr Amelia",
            "Dr Ava",
            "Dr Avery",
            "Dr Asher",
            "Dr Aiden",
            "Dr Abigail",
            "Dr Anthony",
            "Dr Aria",
            "Dr Aurora",
            "Dr Angel"
        )
        yoeList = arrayOf(
            "10 years of experience",
            "10 years of experience",
            "10 years of experience",
            "10 years of experience",
            "10 years of experience",
            "10 years of experience",
            "10 years of experience",
            "10 years of experience",
            "10 years of experience",
            "10 years of experience"
        )
        specializationList = arrayOf(
            "Gynaecologist",
            "Pediatrician",
            "Gynaecologist",
            "Pediatrician",
            "Gynaecologist",
            "Pediatrician",
            "Gynaecologist",
            "Pediatrician",
            "Gynaecologist",
            "Pediatrician"
        )

        doctors = arrayListOf<AvailableDoctorsDC>()

        getAvailableDoctors()
    }

    private fun getAvailableDoctors() {
        for (i in doctorNameList.indices) {
            val comment =
                AvailableDoctorsDC(doctorNameList[i], yoeList[i], specializationList[i])
            doctors.add(comment)
        }
        val adapter = AvailableDoctorsAdapter(doctors)
        availableDoctorsListRV.adapter = adapter

        adapter.setOnItemClickListener(object : AvailableDoctorsAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {

//                 Toast.makeText(this@BookAppointmentActivity, "You Clicked on item no. $position", Toast.LENGTH_SHORT).show()

                showDialog();
//                val intent = Intent(this, PostViewActivity::class.java)
//                intent.putExtra("posterName",postList[position].posterName)
//                intent.putExtra("description",postList[position].description)
//                intent.putExtra("upvotes",postList[position].upvotes)
//                intent.putExtra("downvotes",postList[position].downvotes)
//                intent.putExtra("comments",postList[position].comments+" Comments")
//                startActivity(intent)
            }
        })
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(com.androrubin.genesis.R.layout.bottom_sheet_layout)

        dialog.show()
        dialog.getWindow()?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.getWindow()?.getAttributes()?.windowAnimations = com.androrubin.genesis.R.style.DialogAnimation
        dialog.getWindow()?.setGravity(Gravity.BOTTOM)
    }
}
