package com.androrubin.genesis.Onboarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.androrubin.genesis.R
import kotlinx.android.synthetic.main.fragment_fifth.view.*


class FifthFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_fifth, container, false)


        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        view.next5.setOnClickListener {

            findNavController().navigate(R.id.action_viewPagerFragment_to_loginActivity)
            onBoardingFinished()

        }
        view.prev.setOnClickListener {
            viewPager?.currentItem=3

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