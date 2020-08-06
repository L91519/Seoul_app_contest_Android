package com.example.myapplication.tmp2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.tmp1.TmpFragment1

class TmpActivity2: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tmp2)

        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.add(R.id.fragment1, TmpFragment1())
        ft.commit()
    }
}