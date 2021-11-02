package com.simple.app.ocbc_project_task.features.dashboard.ui

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MediatorLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MiniStatementList @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : RecyclerView(context, attrs, defStyle) {

    private val miniStatementListAdapter = MiniStatementListAdapter()

    init {
        adapter = miniStatementListAdapter
        layoutManager = LinearLayoutManager(context, VERTICAL, false)
    }

    // set item
    fun setItems(items: List<TransferStatementUiData?>) {
        if (items.isEmpty()) return
        miniStatementListAdapter.setItems(items)
    }

    fun setOnClickListener(listener: ((TransferStatementUiData) -> Unit)?) {
        miniStatementListAdapter.setOnClickListener(listener)
    }
}

fun MiniStatementList.bindItems(
    lifecycleOwner: LifecycleOwner,
    source: MediatorLiveData<List<TransferStatementUiData?>>
) = apply {
    source.observe(lifecycleOwner) { setItems(it) }
}