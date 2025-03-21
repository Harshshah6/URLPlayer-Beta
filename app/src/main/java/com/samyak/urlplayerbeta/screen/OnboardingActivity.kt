package com.samyak.urlplayerbeta.screen

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import com.samyak.urlplayerbeta.R
import com.samyak.urlplayerbeta.databinding.ActivityOnboardingBinding
import com.samyak.urlplayerbeta.utils.LanguageManager
import java.util.Locale

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Auto-detect system language on first run
        val prefs = getSharedPreferences("settings", Context.MODE_PRIVATE)
        if (!prefs.contains("language_set")) {
            // Get system language
            val systemLang = Locale.getDefault().language

            // Check if we support this language
            val supportedLanguages = LanguageManager.getSupportedLanguages()
            val isSupported = supportedLanguages.any { it.second == systemLang }

            if (isSupported) {
                // Set app language to system language
                prefs.edit() {
                    putString("language", systemLang)
                        .putBoolean("language_set", true)
                }

                // Recreate to apply language
                recreate()
            } else {
                // Mark as set but keep default (English)
                prefs.edit() {
                    putBoolean("language_set", true)
                }
            }
        }

        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var index = 0;
        binding.nextBtn.setOnClickListener {
            index = if (index == 2) 0 else index + 1
            animateDot(dot = binding.dot1, false)
            animateDot(dot = binding.dot2, false)
            animateDot(dot = binding.dot3, false)
            when (index) {
                0 -> {
                    animateDot(dot = binding.dot1, true)
                    animateText(binding.title, "Welcome to URLPlayer Beta")
                    animateText(binding.description, "The ultimate video player that supports various formats, streaming protocols, and more. Enjoy seamless playback with powerful features.")
                }

                1 -> {
                    animateDot(dot = binding.dot2, true)
                    animateText(binding.title, "Multiple Video Formats & Streaming Protocols")
                    animateText(binding.description, "URLPlayer supports HLS, MP4, AVI, MKV, and more! Stream content via HTTP, RTMP, RTSP, and other protocols effortlessly.")
                }

                2 -> {
                    animateDot(dot = binding.dot3, true)
                    animateText(binding.title, "Picture-in-Picture & Customizable Controls")
                    animateText(binding.description, "Enjoy a seamless experience with PiP mode, gesture controls for brightness/volume, and ad-free video playback.")
                }

                else -> {

                }
            }
        }

    }

    private fun animateText(textView: TextView, newText:String){
        textView.animate().alpha(0f).setDuration(50).withEndAction {
            // After fading out, change the text
            textView.text = newText

            // Then fade the text back in
            textView.animate().alpha(1f).setDuration(50).start()
        }.start()
//        textView.text = newText
    }

    private fun animateDot(dot: View, isActive: Boolean) {
        dot.setBackgroundResource(
            if (isActive) R.drawable.round_dot_active_shape else R.drawable.round_dot_inactive_shape
        )

//        // If you want to animate the background change, animate the alpha of the view
        dot.animate().alpha(0f).setDuration(50).withEndAction {
            // Change the background resource after fading out
            dot.setBackgroundResource(
                if (!isActive) R.drawable.round_dot_inactive_shape else R.drawable.round_dot_active_shape
            )
            // Fade back in
            dot.animate().alpha(1f).setDuration(50).start()
        }.start()
    }

}