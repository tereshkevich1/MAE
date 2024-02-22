package com.example.cab.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserDataViewModel : ViewModel() {

    private var _username = MutableLiveData("unknown")
    val username: LiveData<String> get() = _username

    private var _phone = MutableLiveData("undefined")
    val phone: LiveData<String> get() = _phone

    private var _route: MutableLiveData<Pair<String?, String?>> =
        MutableLiveData(Pair("", ""))
    val route: LiveData<Pair<String?, String?>> get() = _route


    fun changeUsername(newUsername: String?) {
        if (!newUsername.isNullOrEmpty()) {
            _username.value = newUsername
        }
    }

    fun changePhone(newPhone: String?) {
        if (!newPhone.isNullOrEmpty()) {
            _phone.value = newPhone
        }
    }

    fun setRoute(route: Pair<String?, String?>) {
        if (!route.first.isNullOrEmpty() && !route.second.isNullOrEmpty()) {
            _route.value = route
        }
    }

}