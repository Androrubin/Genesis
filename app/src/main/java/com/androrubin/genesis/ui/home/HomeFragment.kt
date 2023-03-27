package com.androrubin.genesis.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.androrubin.genesis.DietPlanning
import com.androrubin.genesis.R
import com.androrubin.genesis.databinding.FragmentHomeBinding
import com.androrubin.genesis.login_and_splash.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private  lateinit var auth : FirebaseAuth

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root



        binding.cardDiet.setOnClickListener {

            val intent = Intent(context,DietPlanning::class.java)
            startActivity(intent)

        }



        return root
    }





}


