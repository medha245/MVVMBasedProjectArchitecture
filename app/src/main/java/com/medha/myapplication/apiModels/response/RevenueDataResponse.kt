package com.medha.myapplication.apiModels.response

import com.google.gson.annotations.SerializedName
import com.medha.myapplication.api.BaseParserClass
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject

class RevenueDataResponse : BaseParserClass() {
    var status: Boolean = false
    var data: RevenueData = RevenueData()
    var error: RevenueError = RevenueError()

    override fun parser(responseBody: Any?,callHistory: ArrayList<Any>?): Any? {
        val parsedResponse = RevenueDataResponse()
        if (responseBody != null && responseBody is ResponseBody) {
            try {

                val rootJo = JSONObject(responseBody.string())

                parsedResponse.status = rootJo.optBoolean("status") || rootJo.optString("type").equals("success", true);

                val dataJa: JSONArray? = rootJo.optJSONArray("data");

                if (dataJa != null && dataJa.length() > 0) {
                    val dataJo = dataJa.getJSONObject(0);

                    parsedResponse.data = RevenueData(
                        dataJo.optString("total_revenue"), dataJo.optString("yesterday_revenue"),
                        dataJo.optString("yesterday_order"), dataJo.optString("weekly_revenue"),
                        dataJo.optString("monthly_revenue"), dataJo.optString("quarterly_revenue"),
                        dataJo.optString("yearly_revenue")
                    )
                }


                val errorJa: JSONArray? = rootJo.optJSONArray("error");

                if (errorJa != null && errorJa.length() > 0) {
                    val errorJo = errorJa.getJSONObject(0);

                    parsedResponse.error = RevenueError(
                        errorJo.optString("code"), errorJo.optString("key"),
                        errorJo.optString("message")
                    )
                }

            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
        return parsedResponse
    }
}

class RevenueData(
    @SerializedName("total_revenue") var totalRevenue: String = "",
    @SerializedName("yesterday_revenue") var yesterdayRevenue: String = "",
    @SerializedName("yesterday_order") var yesterdayOrderCount: String = "",
    @SerializedName("weekly_revenue") var weeklyRevenue: String = "",
    @SerializedName("monthly_revenue") var monthlyRevenue: String = "",
    @SerializedName("quarterly_revenue") var quarterlyRevenue: String = "",
    @SerializedName("yearly_revenue") var yearlyRevenue: String = ""
) {

    fun getTotalRevenueDouble(): Double {
        return try {
            totalRevenue.trim().toDouble()
        } catch (e: java.lang.NumberFormatException) {
            0.0
        }
    }

    fun getWeeklyRevenueDouble(): Double {
        return try {
            weeklyRevenue.trim().toDouble()
        } catch (e: java.lang.NumberFormatException) {
            0.0
        }
    }

    fun getMonthlyRevenueDouble(): Double {
        return try {
            monthlyRevenue.trim().toDouble()
        } catch (e: java.lang.NumberFormatException) {
            0.0
        }
    }

    fun getQuarterlyRevenueInt(): Double {
        return try {
            quarterlyRevenue.trim().toDouble()
        } catch (e: java.lang.NumberFormatException) {
            0.0
        }
    }

    fun getYearlyRevenueInt(): Double {
        return try {
            yearlyRevenue.trim().toDouble()
        } catch (e: java.lang.NumberFormatException) {
            0.0
        }
    }

}

class RevenueError(
    @SerializedName("code") var code: String = "",
    @SerializedName("key") var key: String = "",
    @SerializedName("message") var message: String = ""
)