package com.androrubin.genesis.Onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.androrubin.genesis.R
import kotlinx.android.synthetic.main.fragment_second.view.*


class SecondFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_second, container, false)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        view.next2.setOnClickListener {
            viewPager?.currentItem=2

        }
        view.prev.setOnClickListener {
            viewPager?.currentItem=0

        }
        return view
    }


}