package ru.netology.nmedia.utils

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class SingleLiveEvent<T> : MutableLiveData<T>() {
    private var pending = false
    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T?>) {
        require (!hasActiveObservers()) {
            error("Multiple observers registered but only one will be notified of changes.")
        }

        super.observe(owner) {
            if (pending) {
                pending = false
                observer.onChanged(it)
            }
        }
    }
    @MainThread
    override fun setValue(t: T?) {
        pending = true
        super.setValue(t)
    }
}