package com.androrubin.genesis.Onboarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.androrubin.genesis.R
import kotlinx.android.synthetic.main.fragment_first.view.*
import kotlinx.android.synthetic.main.fragment_view_pager.*


class FirstFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_first, container, false)

        view.next1.setOnClickListener {
            viewPager?.currentItem=1

        }
        view.skip.setOnClickListener {

            findNavController().navigate(R.id.action_viewPagerFragment_to_loginActivity)
            onBoardingFinished()

        }

        return view
    }

    private fun onBoardingFinished(){
        val setsharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = setsharedPref.edit()
        editor.putBoolean("Finished",true)
        editor.apply()
    }


}