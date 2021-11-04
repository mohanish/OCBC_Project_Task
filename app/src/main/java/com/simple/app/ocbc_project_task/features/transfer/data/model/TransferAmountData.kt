package com.simple.app.ocbc_project_task.features.transfer.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@Keep
data class TransferAmountData(
    @SerializedName("data") val `data`: Data = Data(),
    @SerializedName("status") val status: String = "" // success
) {
    @Keep
    data class Data(
        @SerializedName("amount") val amount: BigDecimal? = null, // 100
        @SerializedName("date") val date: String? = null, // 2021-09-12T00:00:00.000Z
        @SerializedName("description") val description: String = "", // room rental
        @SerializedName("id") val id: String = "", // 4a75bc0a-dccd-4d83-b5d8-4a9178894a19
        @SerializedName("recipientAccountNo") val recipientAccountNo: String = "" // 4992-992-9021
    )
}