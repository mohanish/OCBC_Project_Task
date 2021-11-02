package com.simple.app.ocbc_project_task.common.core.interceptors

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("token") val token: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6Im9jYmMiLCJpYXQiOjE2MzU4MzU0NTQsImV4cCI6MTYzNTg0NjI1NH0.-9trgZcJMnDH-aF4huyWiTqiTg3mPmpumf-8lw3UvwI",
)