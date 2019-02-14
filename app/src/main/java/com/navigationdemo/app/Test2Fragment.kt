package com.navigationdemo.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

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

    }
}