package com.navigationdemo.app

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.Navigation

/**
 * 当前类注释:
 * <p>
 * Author : LeonWang <p>
 * Created: 2019/2/15.1:28 PM <P>
 * Description:
 * <p>
 * Copyright (C) 2019 lijiawangjun@gmail.com. All rights reserved.
 */
class Test2Fragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_test_2, container, false)
        initEvent(view)
        return view
    }

    private fun initEvent(view: View) {
        val btnStartTest2 = view.findViewById<AppCompatButton>(R.id.btn)
        btnStartTest2.setOnClickListener {
//            val bundle = bundleOf("paramsA" to "大王叫我来巡山")
//            Navigation.findNavController(btnStartTest2).navigate(R.id.action_test2_to_test3)
            val directions = Test2FragmentDirections.actionTest2ToTest3("大王叫我来巡山")
            Navigation.findNavController(btnStartTest2).navigate(directions)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Test2Fragment","onCreate")
    }
}