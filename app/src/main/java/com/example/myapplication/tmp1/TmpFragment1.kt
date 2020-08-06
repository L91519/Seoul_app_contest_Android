package com.example.myapplication.tmp1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.myapplication.SharedDialogFragment
import kotlinx.android.synthetic.main.fragment_tmp1.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TmpFragment1 : Fragment() {

    val viewmodel1: TmpActivityViewModel1 by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =
            inflater.inflate(com.example.myapplication.R.layout.fragment_tmp1, container, false)

        viewmodel1._message.observe(viewLifecycleOwner, MyObserver())

        view.btn_show_dialog1.setOnClickListener { view ->
            SharedDialogFragment.newInstance("tmp1", viewmodel1)
                .show(parentFragmentManager, "")
        }

        view.btn_to_tmp2.setOnClickListener { view ->
            val intent = Intent(activity, TmpActivity1::class.java)
            activity?.startActivity(intent)
        }

        return view
    }

}

class MyObserver : Observer<String?> {
    override fun onChanged(it: String?) {
        Log.d("-", "-")
    }
}