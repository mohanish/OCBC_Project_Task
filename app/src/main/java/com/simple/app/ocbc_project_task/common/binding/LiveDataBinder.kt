package com.simple.app.ocbc_project_task.common.binding

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

class LiveDataBinder(private val lifecycle: Lifecycle) : LifecycleOwner {
    override fun getLifecycle(): Lifecycle {
        return lifecycle
    }

    /**
     * Convenient function to bind an [Observer] to a [LiveData] instance without
     * the need to specify a [LifecycleOwner].
     */
    fun <T> LiveData<T>.observe(observer: Observer<T>) {
        this.observe(this@LiveDataBinder, observer)
    }
}

/**
 * Gets the [LiveDataBinder] instance of this [Fragment].
 */
fun Fragment.withViewLifecycleOwner(func: LiveDataBinder.() -> Unit) {
    func.invoke(LiveDataBinder(viewLifecycleOwner.lifecycle))
}

/**
 * Gets the [LiveDataBinder] instance of this [AppCompatActivity].
 */
fun AppCompatActivity.withViewLifecycleOwner(func: LiveDataBinder.() -> Unit) {
    func.invoke(LiveDataBinder(lifecycle))
}