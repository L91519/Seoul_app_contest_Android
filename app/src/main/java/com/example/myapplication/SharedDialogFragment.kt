package com.example.myapplication

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SharedDialogFragment<T: BaseViewModel> : DialogFragment() {
    var message: String = ""
    var vm: T by viewModel()

    companion object{
        fun<T: BaseViewModel> newInstance(message: String, vm: T) = SharedDialogFragment<T>().apply{
            arguments = Bundle().apply {
                putString("message", message)
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        arguments?.getString("message")
        vm?.setMessage(message)
        return super.onCreateDialog(savedInstanceState)
    }
}

