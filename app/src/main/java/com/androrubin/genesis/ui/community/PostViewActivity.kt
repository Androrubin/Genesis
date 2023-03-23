package com.androrubin.genesis.ui.community

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androrubin.genesis.R
import com.androrubin.genesis.ui.community.adaptersAndDC.CommentsDC

class PostViewActivity : AppCompatActivity() {

    lateinit var commentorNameList: Array<String>
    lateinit var commentsList : Array<String>
    lateinit var comments : ArrayList<CommentsDC>
    private lateinit var posterName : TextView
    private lateinit var description : TextView
    private lateinit var upvotes : TextView
    private lateinit var downvotes : TextView
    private lateinit var noOfcomments : TextView
    private lateinit var commentsRV : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_view)

        commentorNameList = arrayOf(
            "Amelia",
            "Ava",
            "Avery",
            "Asher",
            "Aiden",
            "Abigail",
            "Anthony",
            "Aria",
            "Aurora",
            "Angel"
        )

        commentsList = arrayOf(
            "Biden aims to expand vaccines for adults and children",
            "Just got my first shot, helping the world to be a safer place",
            "Local trains to be suspended in Bengal from tomorrow in view of covid-19",
            "MHA asks states,UTs to ensure there are no fires in hospitals",
            "Australian citizen sues PM Morrison over flight ban from india",
            "Former India hockey coach Kaushik hospitalised after testing positive for COVID",
            "Delhi records 20,960 fresh covid-19 infections, positivity rate at 26.37%",
            "Barcelona church offers open-air space for Ramadan",
            "Trillions of cicadas set to emerge in the US, here's why",
            "Homemaker, economist: Candidates from all walks of life in Bengal assembly"
        )

        posterName = findViewById(R.id.posterNameEdt)
        description = findViewById(R.id.descriptionEdt)
        upvotes = findViewById(R.id.upvotesEdt)
        downvotes = findViewById(R.id.downvotesEdt)
        noOfcomments = findViewById(R.id.commentsEdt)
        commentsRV = findViewById(R.id.commentsRv)
        commentsRV.layoutManager = LinearLayoutManager(this)
        posterName.text = intent.getStringExtra("posterName")
        description.text = intent.getStringExtra("description")
        upvotes.text = intent.getStringExtra("upvotes")
        downvotes.text = intent.getStringExtra("downvotes")
        noOfcomments.text = intent.getStringExtra("comments")

        comments = arrayListOf<CommentsDC>()

        getComments()



    }

    private fun getComments() {
        for (i in commentorNameList.indices) {
            val comment =
                CommentsDC(commentorNameList[i], commentsList[i])
            comments.add(comment)
        }
        val adapter = CommentsAdapter(comments)
            commentsRV.adapter = adapter
    }
}