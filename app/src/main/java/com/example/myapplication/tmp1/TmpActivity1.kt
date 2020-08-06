package com.example.myapplication.tmp1

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class TmpActivity1: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_tmp1)

        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.add(R.id.fragment1, TmpFragment1())
        ft.commit()

    }
}