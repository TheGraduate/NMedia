package ru.netology.nmedia.activity

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import ru.netology.nmedia.R
import ru.netology.nmedia.auth.AppAuth

class AuthAskFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(R.layout.fragment_auth_ask)
            //.setTitle("Вы уверены?")
            .setPositiveButton(R.string.yes) { _, _ ->
                AppAuth.getInstance().removeAuth()
                dialog?.dismiss()
            }
            .setNegativeButton(R.string.no) { _, _ ->
                dialog?.dismiss()
            }
        return builder.create()
    }
}