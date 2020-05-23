package dev.beriashvili.exams.firstmidterm.networking

interface RequestCallback {
    fun onError(throwable: Throwable)
    fun onSuccess(response: String)
}
