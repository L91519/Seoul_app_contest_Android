package com.example.myapplication

import com.example.myapplication.tmp1.TmpActivityViewModel1
import com.example.myapplication.tmp2.TmpActivityViewModel2
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { TmpActivityViewModel1() }
    viewModel { TmpActivityViewModel2() }
}