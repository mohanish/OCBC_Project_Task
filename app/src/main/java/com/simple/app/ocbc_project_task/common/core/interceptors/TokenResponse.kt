package com.simple.app.ocbc_project_task.common.core.interceptors

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("status") val status: String = "",
    @SerializedName("token") var token: String = "",
)