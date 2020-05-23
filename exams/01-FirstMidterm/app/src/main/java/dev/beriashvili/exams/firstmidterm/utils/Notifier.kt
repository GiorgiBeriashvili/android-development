package dev.beriashvili.exams.firstmidterm.utils

import android.content.Context
import android.widget.Toast

object Notifier {
    fun showToast(context: Context, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}