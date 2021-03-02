package com.sahil.practicaltask.extensions

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified T : ViewModel> Fragment.getViewModel(noinline creator: (() -> T)? = null): T {
    return if (creator == null)
        ViewModelProvider(this).get(T::class.java)
    else
        ViewModelProvider(this,
            BaseViewModelFactory(
                creator
            )
        ).get(T::class.java)
}

inline fun <reified T : ViewModel> Activity.getViewModel(noinline creator: (() -> T)? = null): T {
    val fragmentActivity = this as FragmentActivity
    return if (creator == null)
        ViewModelProvider(fragmentActivity).get(T::class.java)
    else
        ViewModelProvider(fragmentActivity,
            BaseViewModelFactory(
                creator
            )
        ).get(T::class.java)
}

@Suppress("UNCHECKED_CAST")
class BaseViewModelFactory<T>(val creator: () -> T) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return creator() as T
    }
}