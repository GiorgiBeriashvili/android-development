package dev.beriashvili.assignments.mvvm.networking

interface RequestCallback {
    fun onError(throwable: Throwable)
    fun onSuccess(response: String)
}