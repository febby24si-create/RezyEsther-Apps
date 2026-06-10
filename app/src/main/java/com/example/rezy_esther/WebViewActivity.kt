package com.example.rezy_esther

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.rezy_esther.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    companion object {
        // URL default aplikasi
        private const val SIPEDES_URL = "https://rei-sipedes.alwaysdata.net/login"

        // Key untuk Intent extras dari berita
        const val EXTRA_URL   = "NEWS_URL"
        const val EXTRA_TITLE = "NEWS_TITLE"
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        // ✅ Ambil URL & title dari Intent (dari klik berita), fallback ke SiPedes
        val targetUrl   = intent.getStringExtra(EXTRA_URL)   ?: SIPEDES_URL
        val targetTitle = intent.getStringExtra(EXTRA_TITLE) ?: "Website SiPedes"

        supportActionBar?.title = targetTitle

        binding.toolbar.setNavigationOnClickListener {
            if (binding.webView.canGoBack()) binding.webView.goBack()
            else onBackPressedDispatcher.onBackPressed()
        }

        setupWebView()
        binding.webView.loadUrl(targetUrl)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        binding.webView.settings.apply {
            javaScriptEnabled   = true
            domStorageEnabled   = true
            loadWithOverviewMode = true
            useWideViewPort     = true
            setSupportZoom(true)
            builtInZoomControls  = true
            displayZoomControls  = false
            userAgentString      = userAgentString.replace("; wv", "")
        }

        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.progressBar.visibility = View.VISIBLE
                binding.webView.visibility     = View.INVISIBLE
            }
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.progressBar.visibility = View.GONE
                binding.webView.visibility     = View.VISIBLE
                binding.webView.animate().alpha(1f).setDuration(300).start()
            }
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?) = false
        }

        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                binding.progressBar.progress = newProgress
                if (newProgress == 100) binding.progressBar.visibility = View.GONE
            }
            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                if (!title.isNullOrEmpty() && title != "about:blank") {
                    supportActionBar?.title = title
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (binding.webView.canGoBack()) binding.webView.goBack()
        else super.onBackPressed()
    }
}