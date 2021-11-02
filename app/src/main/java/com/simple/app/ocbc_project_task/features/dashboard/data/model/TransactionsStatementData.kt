package com.simple.app.ocbc_project_task.features.dashboard.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.util.*

@Keep
data class TransactionsStatementData(
    @SerializedName("data") val `data`: List<Data> = listOf(),
    @SerializedName("status") val status: String = "" // success
) {
    @Keep
    data class Data(
        @SerializedName("amount") val amount: BigDecimal? = BigDecimal.ZERO, // 18.5
        @SerializedName("currency") val currency: String? = "", // SGD
        @SerializedName("date") val date: Date? = null, // 2021-09-12T02:13:03.054Z
        @SerializedName("description") val description: String? = "", // null
        @SerializedName("from") val from: From = From(),
        @SerializedName("id") val id: String = "", // 39222a3e-3890-4091-8807-d92707355f8c
        @SerializedName("type") val type: String? = "" // receive
    ) {
        @Keep
        data class From(
            @SerializedName("accountHolderName") val accountHolderName: String = "", // Max Yee
            @SerializedName("accountNo") val accountNo: String = "" // 1234-000-1234
        )
    }
}