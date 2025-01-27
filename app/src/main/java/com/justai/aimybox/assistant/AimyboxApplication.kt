package com.justai.aimybox.assistant

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.justai.aimybox.Aimybox
import com.justai.aimybox.api.aimybox.AimyboxDialogApi
import com.justai.aimybox.components.AimyboxProvider
import com.justai.aimybox.core.Config
import com.justai.aimybox.speechkit.google.platform.GooglePlatformSpeechToText
import com.justai.aimybox.speechkit.google.platform.GooglePlatformTextToSpeech
import java.util.*

class AimyboxApplication : Application(), AimyboxProvider {

    companion object {
        private const val AIMYBOX_API_KEY = "Ldf0j7WZi3KwNah2aNeXVIACz0lb9qMH"
    }

    override val aimybox by lazy { createAimybox(this) }

    private fun createAimybox(context: Context): Aimybox {
        val unitId = UUID.randomUUID().toString()

        @Suppress("DEPRECATION") // TODO
        val locale = resources.configuration.locale
        val textToSpeech = GooglePlatformTextToSpeech(context, locale)
        val speechToText = GooglePlatformSpeechToText(context, locale)

        val dialogApi = AimyboxDialogApi(AIMYBOX_API_KEY, unitId)

        val aimyboxConfig = Config.create(speechToText, textToSpeech, dialogApi)
        @Suppress("DEPRECATION") // TODO
        return Aimybox(aimyboxConfig)
    }
}