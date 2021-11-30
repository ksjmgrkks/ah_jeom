package com.ssac.ah_jeom.src.main.peek.bestStorage.models

import com.google.gson.annotations.SerializedName

data class GetBestStorageResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ResultGetBestStorage
)