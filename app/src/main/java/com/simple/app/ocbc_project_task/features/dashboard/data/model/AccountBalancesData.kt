package com.simple.app.ocbc_project_task.features.dashboard.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

@Keep
class AccountBalancesData {

    @SerializedName("status")
    val status: String = "" // success

    @SerializedName("balance")
    val availableBalance: BigDecimal? = null // 18.5
}