package ru.netology.nmedia.activity

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import dagger.hilt.android.AndroidEntryPoint
import ru.netology.nmedia.R
import ru.netology.nmedia.auth.AppAuth
import javax.inject.Inject

@AndroidEntryPoint
class AuthAskFragment: DialogFragment() {
    @Inject
    lateinit var auth: AppAuth
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(R.layout.fragment_auth_ask)
            .setPositiveButton(R.string.yes) { _, _ ->
                auth.removeAuth()
                dialog?.dismiss()
            }
            .setNegativeButton(R.string.no) { _, _ ->
                dialog?.dismiss()
            }
        return builder.create()
    }
}