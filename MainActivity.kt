/*
 * ===== SETUP =====
 * 1) Android Studio -> New Project -> "Empty Views Activity" (Kotlin), package: com.example.livetranslator
 * 2) app/build.gradle(.kts) dependencies:
 *      implementation("com.google.mlkit:translate:17.0.3")
 *      implementation("androidx.appcompat:appcompat:1.7.0")
 * 3) AndroidManifest.xml (inside <manifest>, before <application>):
 *      <uses-permission android:name="android.permission.RECORD_AUDIO"/>
 *      <uses-permission android:name="android.permission.INTERNET"/>
 *      <queries><intent><action android:name="android.speech.RecognitionService"/></intent></queries>
 * 4) Replace MainActivity.kt with this file. Run on a real phone (not emulator).
 *
 * ===== HOW TO USE WITH TELEGRAM CALL =====
 * - Telegram call: speaker ON. Earphone/Bluetooth recommended to hear Bengali.
 * - Button "শুনুন": other person speaks -> you hear Bengali.
 * - Button "বলুন": you speak Bengali -> phone loudspeaker says it in their language
 *   (Telegram mic picks it up). Use one button at a time.
 */
package com.example.livetranslator

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import java.util.Locale

class MainActivity : AppCompatActivity() {

    // নাম -> (ML Kit code, Locale tag)
    private val langs = linkedMapOf(
        "English" to Pair(TranslateLanguage.ENGLISH, "en-US"),
        "हिन्दी (Hindi)" to Pair(TranslateLanguage.HINDI, "hi-IN"),
        "العربية (Arabic)" to Pair(TranslateLanguage.ARABIC, "ar-SA"),
        "Español (Spanish)" to Pair(TranslateLanguage.SPANISH, "es-ES"),
        "Français (French)" to Pair(TranslateLanguage.FRENCH, "fr-FR"),
        "中文 (Chinese)" to Pair(TranslateLanguage.CHINESE, "zh-CN"),
        "Русский (Russian)" to Pair(TranslateLanguage.RUSSIAN, "ru-RU"),
        "Português (Portuguese)" to Pair(TranslateLanguage.PORTUGUESE, "pt-BR"),
        "日本語 (Japanese)" to Pair(TranslateLanguage.JAPANESE, "ja-JP"),
