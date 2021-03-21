package com.medha.myapplication.apiModels

import android.util.Log
import okhttp3.ResponseBody
import org.json.JSONObject

/**
 * A helper class : If you are lucky enough to have an api structure at backend where
 * they provide errors in api in a standard format , you can parse the standard error format
 * the resource class will automatically give you this error message on error you dont need to
 * specifically parse it for each api
 */
class ErrorUtils {

    companion object {

        fun parseError(errorBody: ResponseBody?): ApiError {
            var apiError: ApiError

            if (errorBody == null) {
                apiError = ApiError()
                apiError.errorMessage = "Something went wrong"
                return apiError
            }
            try {
                apiError = ApiError()
                val responseStr = errorBody.string()
                val jsonObject = JSONObject(responseStr)

                val message = jsonObject.optString("message")
                apiError.success = jsonObject.optBoolean("success")
                val errorMessage = jsonObject.optString("error")

                if (jsonObject.optJSONObject("errors") != null) {
                    val errorsObject = jsonObject.optJSONObject("errors")
                    val errorPickupLOcationArray = errorsObject?.optJSONArray("pickup_location")
                    if ((errorPickupLOcationArray?.length() ?: 0) > 0) {
                        apiError.errorMessage = errorPickupLOcationArray?.get(0).toString()
                        return apiError
                    }
                    val nameArray = errorsObject?.optJSONArray("name")
                    if ((nameArray?.length() ?: 0) > 0) {
                        apiError.errorMessage = nameArray?.get(0).toString()
                        return apiError
                    }
                    val lnameArray = errorsObject?.optJSONArray("lname")
                    if ((lnameArray?.length() ?: 0) > 0) {
                        apiError.errorMessage = lnameArray?.get(0).toString()
                        return apiError
                    }
                    val addressArray = errorsObject?.optJSONArray("address")
                    if ((addressArray?.length() ?: 0) > 0) {
                        apiError.errorMessage = addressArray?.get(0).toString()
                        return apiError
                    }

                    val phoneArray = errorsObject?.optJSONArray("phone")
                    if ((phoneArray?.length() ?: 0) > 0) {
                        apiError.errorMessage = phoneArray?.get(0).toString()
                        return apiError
                    }
                    /**
                     * {"status_code":422,"errors":{"message":"Preferred date cannot be on a Sunday.","
                     * errors":{"preferred_date":["Preferred date cannot be on a Sunday."]}}}
                     */
                    val innerError = errorsObject?.optJSONObject("errors")
                    val dateArray = innerError?.optJSONArray("preferred_date")
                    if ((dateArray?.length() ?: 0) > 0) {
                        apiError.errorMessage = dateArray?.get(0).toString()
                        return apiError
                    }
                    val addressArray1 = innerError?.optJSONArray("address1")
                    if ((addressArray1?.length() ?: 0) > 0) {
                        apiError.errorMessage = addressArray1?.get(0).toString()
                        return apiError
                    }
                    val phoneArrayInner = innerError?.optJSONArray("phone")
                    if ((phoneArrayInner?.length() ?: 0) > 0) {
                        apiError.errorMessage = phoneArrayInner?.get(0).toString()
                        return apiError
                    }
                    val addressArray2 = innerError?.optJSONArray("address2")
                    if ((addressArray2?.length() ?: 0) > 0) {
                        apiError.errorMessage = addressArray2?.get(0).toString()
                        return apiError
                    }
                    val mobileArray = errorsObject?.optJSONArray("mobile")
                    if ((mobileArray?.length() ?: 0) > 0) {
                        apiError.errorMessage = mobileArray?.get(0).toString()
                        return apiError
                    }
                    val companyArray = errorsObject?.optJSONArray("company_name")
                    if ((companyArray?.length() ?: 0) > 0) {
                        apiError.errorMessage = companyArray?.get(0).toString()
                        return apiError
                    }
                    val landmarkArray = errorsObject?.optJSONArray("landmark")
                    if ((landmarkArray?.length() ?: 0) > 0) {
                        apiError.errorMessage = landmarkArray?.get(0).toString()
                        return apiError
                    }
                    val firstNameArray = errorsObject?.optJSONArray("first_name")
                    if ((firstNameArray?.length() ?: 0) > 0) {
                        apiError.errorMessage = firstNameArray?.get(0).toString()
                        return apiError
                    }
                    val lastNameArray = errorsObject?.optJSONArray("last_name")
                    if ((lastNameArray?.length() ?: 0) > 0) {
                        apiError.errorMessage = lastNameArray?.get(0).toString()
                        return apiError
                    }
                    val businessCategoryArray = errorsObject?.optJSONArray("business_category")
                    if ((businessCategoryArray?.length() ?: 0) > 0) {
                        apiError.errorMessage = businessCategoryArray?.get(0).toString()
                        return apiError
                    }
                    val billingAddressArray = errorsObject?.optJSONArray("billing_address")
                    if ((billingAddressArray?.length() ?: 0) > 0) {
                        apiError.errorMessage = billingAddressArray?.get(0).toString()
                        return apiError
                    }

                    val bankAccountNumber = errorsObject?.optJSONArray("bank_account_number")
                    if ((bankAccountNumber?.length() ?: 0) > 0) {
                        apiError.errorMessage = bankAccountNumber?.get(0).toString()
                        return apiError
                    }

                    val bankAccountType = errorsObject?.optJSONArray("bank_account_type")
                    if ((bankAccountType?.length() ?: 0) > 0) {
                        apiError.errorMessage = bankAccountType?.get(0).toString()
                        return apiError
                    }

                    val beneficiaryName = errorsObject?.optJSONArray("beneficiary_name")
                    if ((beneficiaryName?.length() ?: 0) > 0) {
                        apiError.errorMessage = beneficiaryName?.get(0).toString()
                        return apiError
                    }

                    val bankIfscCode = errorsObject?.optJSONArray("bank_ifsc_code")
                    if ((bankIfscCode?.length() ?: 0) > 0) {
                        apiError.errorMessage = bankIfscCode?.get(0).toString()
                        return apiError
                    }

                    val bankBranch = errorsObject?.optJSONArray("bank_branch")
                    if ((bankBranch?.length() ?: 0) > 0) {
                        apiError.errorMessage = bankBranch?.get(0).toString()
                        return apiError
                    }

                    val confirmBankAccountNumber = errorsObject?.optJSONArray("confirm_bank_account_number")
                    if ((confirmBankAccountNumber?.length() ?: 0) > 0) {
                        apiError.errorMessage = confirmBankAccountNumber?.get(0).toString()
                        return apiError
                    }

                    val bankName = errorsObject?.optJSONArray("bank_name")
                    if ((bankName?.length() ?: 0) > 0) {
                        apiError.errorMessage = bankName?.get(0).toString()
                        return apiError
                    }


                    if (!apiError.errorMessage.isEmpty())
                        return apiError

                } else if (jsonObject.optJSONArray("error") != null) {

                    val errorJa = jsonObject.optJSONArray("error")

                    if (errorJa != null && errorJa.length() > 0) {
                        val errorJo = errorJa.getJSONObject(0)

                        apiError.message = errorJo.optString("message")
                        apiError.success = false

                    }

                }

                if (errorMessage.isNullOrEmpty()) {
                    if (message.isNullOrEmpty()) {
                        apiError.message = "Something went wrong"
                    } else {
                        apiError.errorMessage = message
                    }

                    return apiError
                } else {
                    apiError.errorMessage = errorMessage
                }

                return apiError
            } catch (ex: Throwable) {
                apiError = ApiError()
                apiError.errorMessage = ex.toString()
                Log.e("exception", ex.toString())
                return apiError
            }
        }

    }

}