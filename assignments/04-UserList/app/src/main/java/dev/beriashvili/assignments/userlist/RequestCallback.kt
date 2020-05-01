package dev.beriashvili.assignments.userlist

interface RequestCallback {
    fun onFailed(errorMessage: String)
    fun onSuccess(response: String)
}