package com.justai.aimybox.assistant

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.justai.aimybox.components.AimyboxAssistantFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_main)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val assistantFragment = AimyboxAssistantFragment()

        val buttonRegister = findViewById(R.id.buttonRegister) as Button
        buttonRegister.setOnClickListener {
            val browser = Intent(ACTION_VIEW, Uri.parse("https://writesonic.com?via=chatsonicapp"))
            startActivity(browser)
        }

        val buttonConnect = findViewById(R.id.buttonConnect) as Button
        buttonConnect.setOnClickListener {
            // TODO check API connection
            val layoutRegister = findViewById(R.id.layoutRegister) as View
            layoutRegister.visibility = View.INVISIBLE

            val prompt = findViewById(R.id.prompt) as View
            prompt.visibility = View.VISIBLE
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.assistant_container, assistantFragment)
                commit()
            }
        }
    }

    override fun onBackPressed() {
        val assistantFragment = (supportFragmentManager.findFragmentById(R.id.assistant_container)
                as? AimyboxAssistantFragment)
        if (assistantFragment?.onBackPressed() != true) super.onBackPressed()
    }

}