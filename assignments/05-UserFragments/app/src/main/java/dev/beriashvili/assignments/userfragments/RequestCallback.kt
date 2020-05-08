package dev.beriashvili.assignments.userfragments

interface RequestCallback {
    fun onFailed(errorMessage: String)
    fun onSuccess(response: String)
}
