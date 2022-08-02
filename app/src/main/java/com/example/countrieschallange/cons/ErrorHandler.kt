package com.example.countrieschallange.cons

import android.app.AlertDialog
import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext

class ErrorHandler {
    companion object {
        fun displayError(message: String = "An issue has occurred", context: Context, retry: () -> Unit) {
            AlertDialog.Builder(context)
                .setTitle("Error has occurred")
                .setPositiveButton("Retry") { dialog, _ ->
                    dialog.dismiss()
                    retry()
                }
                .setNegativeButton("Dismiss") { dialog, _ ->
                    dialog.dismiss()
                }
                .setMessage(message)
                .create()
                .show()
        }
    }
}