package com.androrubin.genesis.ui.community

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.androrubin.genesis.databinding.FragmentCommunityBinding
import com.androrubin.genesis.ui.community.adaptersAndDC.CommunityDC

class CommunityFragment : Fragment() {

    private var _binding: FragmentCommunityBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var postList: ArrayList<CommunityDC>
    lateinit var posterName: Array<String>
    lateinit var description: Array<String>
    lateinit var upvotes: Array<String>
    lateinit var downvotes: Array<String>
    lateinit var comments: Array<String>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val dashboardViewModel = ViewModelProvider(this).get(CommunityViewModel::class.java)

        _binding = FragmentCommunityBinding.inflate(inflater, container, false)
        val root: View = binding.root

        postList = arrayListOf<CommunityDC>()
        posterName = arrayOf(
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

        description = arrayOf(
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

        upvotes = arrayOf(
            "10",
            "20",
            "30",
            "40",
            "50",
            "60",
            "70",
            "80",
            "90",
            "30"
        )

        downvotes = arrayOf(
            "10",
            "20",
            "30",
            "40",
            "50",
            "60",
            "70",
            "80",
            "90",
            "30"
        )

        comments = arrayOf(
            "10",
            "20",
            "30",
            "40",
            "50",
            "60",
            "70",
            "80",
            "90",
            "30"
        )

        binding.postsRv.layoutManager = LinearLayoutManager(context)
        getUserData()
       // binding.postsRv.adapter = CommunityAdapter(postList)
        return root
    }

    fun getUserData() {
        for (i in posterName.indices) {
            val post =
                CommunityDC(posterName[i], description[i], upvotes[i], downvotes[i], comments[i])
            postList.add(post)
        }
        val adapter = CommunityAdapter(postList)
        binding.createPostBtn.setOnClickListener {
            startActivity(Intent(context , CreatePostActivity::class.java))
        }
        binding.postsRv.adapter = adapter
        adapter.setOnItemClickListener(object : CommunityAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {

               // Toast.makeText(context, "You Clicked on item no. $position", Toast.LENGTH_SHORT) .show()

                val intent = Intent(context,PostViewActivity::class.java)
                intent.putExtra("posterName",postList[position].posterName)
                intent.putExtra("description",postList[position].description)
                intent.putExtra("upvotes",postList[position].upvotes)
                intent.putExtra("downvotes",postList[position].downvotes)
                intent.putExtra("comments",postList[position].comments+" Comments")
                startActivity(intent)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}