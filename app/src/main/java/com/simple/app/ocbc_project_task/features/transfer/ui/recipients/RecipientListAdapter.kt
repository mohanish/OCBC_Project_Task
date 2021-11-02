package com.simple.app.ocbc_project_task.features.transfer.ui.recipients

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.simple.app.ocbc_project_task.R
import com.simple.app.ocbc_project_task.features.transfer.data.model.PayeesData
import kotlinx.android.synthetic.main.item_recipient.view.*

class RecipientListAdapter() :
    RecyclerView.Adapter<RecipientListAdapter.RecipientsListViewHolder>() {

    private val recipientsList = mutableListOf<PayeesData.Data?>()
    private var onClickListener: ((PayeesData.Data) -> Unit)? = null

    class RecipientsListViewHolder(
        view: View,
        private val onClickListener: (position: Int) -> Unit,
    ) : RecyclerView.ViewHolder(view), View.OnClickListener {

        fun bind(transferStatementUiData: PayeesData.Data?) {
            itemView.tvRecipient.text = transferStatementUiData?.accountHolderName
        }

        override fun onClick(view: View?) {
            if (bindingAdapterPosition == -1) return
            onClickListener.invoke(bindingAdapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipientsListViewHolder {
        val viewLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recipient, parent, false)
        return RecipientsListViewHolder(viewLayout, this::onClickViewHolder)
    }

    override fun onBindViewHolder(holder: RecipientsListViewHolder, position: Int) {
        holder.bind(recipientsList[position])
    }

    override fun getItemCount(): Int = recipientsList.size


    private fun onClickViewHolder(position: Int) {
        recipientsList[position]?.let {
            onClickListener?.invoke(it)
        }
    }

    /**
     * Sets a [listener] to be invoked when an item is clicked.
     */
    fun setOnClickListener(listener: ((PayeesData.Data) -> Unit)?) {
        this.onClickListener = listener
    }

    fun setItems(list: List<PayeesData.Data?>) {
        recipientsList.addAll(list)
        notifyDataSetChanged()
    }

    companion object {
        const val ITEM_TYPE_YEAR_HEADER = 1
        const val ITEM_TYPE_TRANSACTION_DETAILS = 2
    }
}