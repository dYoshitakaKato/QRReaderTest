package com.example.qrreadertest

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

object Utils {
    fun <T> LiveData<T>.observeOnChanged(owner: LifecycleOwner, observer: Observer<T>) {
        var prev : T? = null
        this.observe(owner) {
            if (prev?.equals(it) != true) {
                observer.onChanged(it)
            }
            prev = it
        }
    }

}