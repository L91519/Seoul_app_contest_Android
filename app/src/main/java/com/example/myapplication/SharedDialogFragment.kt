package com.example.myapplication

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class SharedDialogFragment<T: BaseViewModel> : DialogFragment() {
    var message: String = ""
    var vm: T? = null

    companion object{
        fun<T: BaseViewModel> newInstance(message: String) = SharedDialogFragment<T>().apply{
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