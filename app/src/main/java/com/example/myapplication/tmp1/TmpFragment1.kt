package com.example.myapplication.tmp1

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_tmp1.view.*


class TmpFragment1 : Fragment() {

    val viewmodel1: TmpActivityViewModel1 = sharedviewmodel<TmpActivityViewModel1>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tmp1, container, false)

        viewmodel1._message.observe(viewLifecycleOwner, Observer {
            Log.d("-", "-")
        })

        view.btn_show_dialog1.setOnClickListener { view ->
            Log.d("btnSetup", "Selected")
        }

        view.btn_to_tmp2.setOnClickListener { view ->
            val intent = Intent (getActivity(), TmpActivity1::class.java)
            activity?.startActivity(intent)

        }

        return view
    }

}