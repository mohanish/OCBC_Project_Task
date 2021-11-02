package com.simple.app.ocbc_project_task.features.dashboard.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.simple.app.ocbc_project_task.R
import com.simple.app.ocbc_project_task.features.dashboard.ui.MiniStatementListAdapter.MiniStatementListViewHolder
import kotlinx.android.synthetic.main.item_mini_statement.view.*
import kotlinx.android.synthetic.main.item_mini_statement_header.view.*

class MiniStatementListAdapter : RecyclerView.Adapter<MiniStatementListViewHolder>() {

    private val statementDataList = mutableListOf<TransferStatementUiData?>()
    private var onClickListener: ((TransferStatementUiData) -> Unit)? = null

    class MiniStatementListViewHolder(
        view: View,
        private val onClickListener: (position: Int) -> Unit,
    ) : RecyclerView.ViewHolder(view), View.OnClickListener {

        fun bind(transferStatementUiData: TransferStatementUiData?) {
            when (transferStatementUiData) {
                is TransferStatementUiData.MonthHeader -> bindMonthHeader(transferStatementUiData)
                is TransferStatementUiData.MiniStatementItem -> bindStatementItem(
                    transferStatementUiData
                )
            }
        }

        private fun bindMonthHeader(transferStatementUiData: TransferStatementUiData.MonthHeader) {
            itemView.tvStatementMonthHeader.text = transferStatementUiData.month
        }

        private fun bindStatementItem(transferStatementUiData: TransferStatementUiData.MiniStatementItem) {
            itemView.tvTransactionDate.text = transferStatementUiData.date
            itemView.tvTransactionAmount.text =
                "${transferStatementUiData.currency} ${transferStatementUiData.amount}"
            itemView.tvTransactionDescription.text = transferStatementUiData.description
        }

        override fun onClick(view: View?) {
            if (bindingAdapterPosition == -1) return
            onClickListener.invoke(bindingAdapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiniStatementListViewHolder {
        val view = when (viewType) {
            ITEM_TYPE_YEAR_HEADER -> R.layout.item_mini_statement_header
            ITEM_TYPE_TRANSACTION_DETAILS -> R.layout.item_mini_statement
            else -> error("Unknown view type: $viewType")
        }
        val viewLayout = LayoutInflater.from(parent.context).inflate(view, parent, false)
        return MiniStatementListViewHolder(viewLayout, this::onClickViewHolder)
    }

    override fun onBindViewHolder(holder: MiniStatementListViewHolder, position: Int) {
        holder.bind(statementDataList[position])
    }

    override fun getItemCount(): Int = statementDataList.size

    override fun getItemViewType(position: Int): Int {
        return when (statementDataList[position]) {
            is TransferStatementUiData.MonthHeader -> ITEM_TYPE_YEAR_HEADER
            else -> ITEM_TYPE_TRANSACTION_DETAILS
        }
    }

    private fun onClickViewHolder(position: Int) {
        statementDataList[position]?.let {
            onClickListener?.invoke(it)
        }
    }

    /**
     * Sets a [listener] to be invoked when an item is clicked.
     */
    fun setOnClickListener(listener: ((TransferStatementUiData) -> Unit)?) {
        this.onClickListener = listener
    }

    fun setItems(list: List<TransferStatementUiData?>) {
        statementDataList.addAll(list)
        notifyDataSetChanged()
    }

    companion object {
        const val ITEM_TYPE_YEAR_HEADER = 1
        const val ITEM_TYPE_TRANSACTION_DETAILS = 2
    }
}