package com.simple.app.ocbc_project_task.features.dashboard.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

@Keep
class AccountBalancesData {
    @SerializedName("currency")
    val currency: String? = ""

    @SerializedName("availableBalance")
    val availableBalance: BigDecimal? = null // 18.5
}