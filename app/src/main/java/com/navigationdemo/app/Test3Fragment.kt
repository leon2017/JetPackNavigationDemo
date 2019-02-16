package com.navigationdemo.app

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
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
class Test3Fragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_test_3, container, false)
        initEvent(view)
        return view
    }

    private fun initEvent(view: View) {
        val tvArgument = view.findViewById<AppCompatTextView>(R.id.tv_argument)
        val btnStartTest1 = view.findViewById<AppCompatButton>(R.id.btn_1)
        val btnStartTest2 = view.findViewById<AppCompatButton>(R.id.btn_2)
        //接收数据
//        arguments?.also {
//            val paramsC = it.getString("paramsC")
//            tvArgument.text = paramsC
//        }
        arguments?.also {
            val bundle = Test3FragmentArgs.fromBundle(it)
            tvArgument.text = bundle.paramsC
        }

        btnStartTest1.setOnClickListener {
            Navigation.findNavController(btnStartTest1).navigate(R.id.action_test3_to_test1)
        }

        btnStartTest2.setOnClickListener {
            Navigation.findNavController(btnStartTest1).navigate(R.id.action_test3_to_test2)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Test3Fragment","onCreate")
    }
}