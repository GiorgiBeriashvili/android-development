package dev.beriashvili.assignments.userlist

interface CustomCallback {
    fun onFailed(errorMessage: String)
    fun onSuccess(response: String)
}