package com.justai.aimybox.assistant

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.justai.aimybox.components.AimyboxAssistantFragment
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_main)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val assistantFragment = AimyboxAssistantFragment()

        val buttonRegister = findViewById<Button>(R.id.buttonRegister)
        buttonRegister.setOnClickListener {
            val browser = Intent(ACTION_VIEW, Uri.parse("https://writesonic.com?via=chatsonicapp"))
            startActivity(browser)
        }

        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val editKey = findViewById<EditText>(R.id.editKey)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.assistant_container, assistantFragment)
            commit()
        }

        fun switchView(mainView: Boolean) {
            val layoutRegister = findViewById<View>(R.id.layoutRegister)
            layoutRegister.visibility = if (!mainView) View.VISIBLE else View.INVISIBLE

            val prompt = findViewById<View>(R.id.prompt)
            prompt.visibility = if (mainView) View.VISIBLE else View.INVISIBLE

//            val container = findViewById<View>(R.id.assistant_container)
//            container.visibility = if (mainView) View.VISIBLE else View.INVISIBLE

            val buttonSettings = findViewById<Button>(R.id.buttonSettings)
            buttonSettings.visibility = if (mainView) View.VISIBLE else View.INVISIBLE
        }

        val buttonConnect = findViewById<Button>(R.id.buttonConnect)
        buttonConnect.setOnClickListener {
            // TODO check API connection


            // save to preferences
            val editor = sharedPref.edit()
            editor.putString("key", editKey.text.toString())
            editor.apply()

            switchView(true)
        }

        val buttonSettings = findViewById<Button>(R.id.buttonSettings)
        buttonSettings.setOnClickListener {
            switchView(false)

        }

        editKey.setText(sharedPref.getString("key", ""))
        if (editKey.text.toString().length > 0) switchView(mainView = true)

    }

    override fun onBackPressed() {
        val assistantFragment = (supportFragmentManager.findFragmentById(R.id.assistant_container)
                as? AimyboxAssistantFragment)
        if (assistantFragment?.onBackPressed() != true) super.onBackPressed()
    }

    fun apiCall(text: String): Response {
        val client = OkHttpClient()

        val mediaType = "application/json".toMediaTypeOrNull()
        val body = RequestBody.create(mediaType, "{\"enable_google_results\":\"true\",\"enable_memory\":false}")
        val request = Request.Builder()
            .url("https://api.writesonic.com/v2/business/content/chatsonic?engine=premium")
            .post(body)
            .addHeader("accept", "application/json")
            .addHeader("content-type", "application/json")
            .addHeader("X-API-KEY", "e32c1e28-aa3e-4a3b-b057-f17e47be74c6")
            .build()

        val response = client.newCall(request).execute()
        return response
    }

}