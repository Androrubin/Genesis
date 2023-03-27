package com.androrubin.genesis.ui.profile

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.androrubin.genesis.R
import com.androrubin.genesis.databinding.FragmentCommunityBinding
import com.androrubin.genesis.databinding.FragmentProfileBinding
import com.androrubin.genesis.journaling.GetJournal
import com.androrubin.genesis.ui.community.CommunityViewModel

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val dashboardViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.jounalBtn.setOnClickListener {
            val intent= Intent(context,GetJournal::class.java)
            startActivity(intent)
        }


        // binding.postsRv.adapter = CommunityAdapter(postList)
        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

