package com.androrubin.genesis.ui.profile

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.ActivityCompat.finishAffinity
import com.androrubin.genesis.MainActivity
import com.androrubin.genesis.R
import com.androrubin.genesis.databinding.FragmentProfileBinding
import com.androrubin.genesis.journaling.GetJournal
import com.androrubin.genesis.login_and_splash.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var auth : FirebaseAuth
    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        auth= FirebaseAuth.getInstance()

        binding.jounalBtn.setOnClickListener {
            val intent= Intent(context, GetJournal::class.java)
            startActivity(intent)
        }

        binding.logout.setOnClickListener {
            auth.signOut()
            val intent= Intent(context, LoginActivity::class.java)
            startActivity(intent)
            finishAffinity(MainActivity())
        }
        return  view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

}