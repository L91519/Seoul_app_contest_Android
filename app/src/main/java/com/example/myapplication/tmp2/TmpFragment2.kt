package com.example.myapplication.tmp2

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.myapplication.SharedDialogFragment
import com.example.myapplication.tmp1.MyObserver
import com.example.myapplication.tmp1.TmpActivity1
import com.example.myapplication.tmp1.TmpActivityViewModel1
import kotlinx.android.synthetic.main.fragment_tmp2.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TmpFragment2 : Fragment() {
    val viewmodel2: TmpActivityViewModel2 by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(com.example.myapplication.R.layout.fragment_tmp2, container, false)

        viewmodel2._message.observe(viewLifecycleOwner, MyObserver())

        view.btn_show_dialog2.setOnClickListener { view ->
            SharedDialogFragment.newInstance<TmpActivityViewModel2>("tmp2", viewmodel2)
                .show(parentFragmentManager,"")
        }

        view.btn_to_tmp1.setOnClickListener { view ->
            val intent = Intent (activity, TmpActivity1::class.java)
            activity?.startActivity(intent)
        }

        return inflater.inflate(com.example.myapplication.R.layout.fragment_tmp2, container, false)
    }

}