package com.justai.aimybox.assistant

import com.google.gson.JsonObject
import com.justai.aimybox.api.DialogApi
import com.justai.aimybox.api.aimybox.AimyboxRequest
import com.justai.aimybox.api.aimybox.AimyboxResponse
import com.justai.aimybox.api.aimybox.AimyboxUtils
import com.justai.aimybox.model.reply.aimybox.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONObject



/**
 * Aimybox dialog api implementation.
 *
 * @param apiKey your project API key
 * @param unitId unique device identifier
 * @param url Aimybox dialog API URL
 * */
class SonicDialogApi(
    private val apiKey: String,
    private val unitId: String,
    private val url: String = DEFAULT_API_URL,
) : DialogApi<AimyboxRequest, AimyboxResponse>() {

    companion object {
        private const val DEFAULT_API_URL = "https://api.writesonic.com/v2/business/content/chatsonic"
    }

    init {
//        val uri = Uri.parse(url)
//        val baseUrl = uri.scheme?.let { "$it://" }.orEmpty() + uri.authority
//        val path = uri.path ?: "/"
    }

    override fun createRequest(query: String) = AimyboxRequest(query, apiKey, unitId)

    suspend fun resetSession() {
        // TODO clear memory
    }

    override suspend fun send(request: AimyboxRequest): AimyboxResponse {
        println("request$request")
        val jsonResponse = apiCall(request)
        val jsonObject = JsonObject()
        JsonObject.
        return AimyboxResponse(json, AimyboxUtils.parseReplies(json, replyTypes))
    }

    fun apiCall(params: AimyboxRequest): Response {
        val client = OkHttpClient()

        val mediaType = "application/json".toMediaTypeOrNull()
        val jsonObject = JSONObject()
        jsonObject.put("enable_google_results", true)
        jsonObject.put("enable_memory", false) // TODO enable memory
        jsonObject.put("input_text", params.query)
        val jsonString: String = jsonObject.toString()

        val body = RequestBody.create(mediaType, jsonString)
        val request = Request.Builder()
            .url(url + "?engine=premium&language=en") // TODO add device locale instead of 'en'
            .post(body)
            .addHeader("accept", "application/json")
            .addHeader("content-type", "application/json")
            .addHeader("X-API-KEY", params.apiKey)
            .build()

        val response = client.newCall(request).execute()
        return response
    }
}