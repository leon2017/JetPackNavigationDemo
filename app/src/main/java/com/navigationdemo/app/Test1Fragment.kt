package com.navigationdemo.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

/**
 * 当前类注释:
 * <p>
 * Author : LeonWang <p>
 * Created: 2019/2/14.1:27 PM <P>
 * Description:
 * <p>
 * Copyright (C) 2019 lijiawangjun@gmail.com. All rights reserved.
 */
class Test1Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_test_1, container, false)
        initEvent(view)
        return view
    }

    private fun initEvent(view: View) {
        val btnStartTest2 = view.findViewById<AppCompatButton>(R.id.btn)
        btnStartTest2.setOnClickListener {
            Navigation.findNavController(btnStartTest2).navigate(R.id.action_test2)
//            NavHostFragment.findNavController(this).navigate(R.id.action_test2)
        }
    }
}