package com.simple.app.ocbc_project_task.common.binding

import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

/**
 * Bind the text of this [TextView] to this [liveData].
 */
fun AppCompatTextView.bindText(
    lifecycleOwner: LifecycleOwner,
    liveData: LiveData<String>
) = apply {
    liveData.observe(lifecycleOwner) { this.text = it }
}