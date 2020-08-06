package com.example.myapplication.tmp2

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.myapplication.tmp1.TmpActivityViewModel1

class TmpFragment2 : Fragment() {
    val viewmodel2: TmpActivityViewModel1 = sharedviewmodel<TmpActivityViewModel1>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        viewmodel2._message.observe(viewLifecycleOwner, Observer {
            Log.d("-", "-")
        })

        return inflater.inflate(R.layout.fragment_tmp2, container, false)
    }

}