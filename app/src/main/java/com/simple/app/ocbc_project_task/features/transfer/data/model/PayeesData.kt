package com.simple.app.ocbc_project_task.features.transfer.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PayeesData(
    @SerializedName("data") val data: List<Data> = listOf(),
    @SerializedName("status") val status: String = "" // success
) {
    @Keep
    data class Data(
        @SerializedName("accountHolderName") val accountHolderName: String = "", // Jason
        @SerializedName("accountNo") val accountNo: String = "", // 8013-777-3232
        @SerializedName("id") val id: String = "" // 8a6da1a4-6f5f-4b53-9b90-0f8c57661a5d
    )
}