package com.logspace.dynamic_flui_x

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.*
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var progressBar: View
    private var interstitialAd: InterstitialAd? = null
    private lateinit var sharedPreferences: SharedPreferences
    private val PREFS_NAME = "app_prefs"
    private val KEY_LAUNCH_COUNT = "launch_count"
    private val INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-2313560120112536/3748587946"
    private val AD_SHOW_INTERVAL = 3 // Her 3 girişte bir

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Android 15 (SDK 35) uyumlu uçtan uca görünüm
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // SharedPreferences başlat
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        
        // Google Ads'i başlat
        MobileAds.initialize(this) {}
        
        webView = findViewById(R.id.webView)
        progressBar = findViewById(R.id.progressBar)

        setupWebView()
        setupBackPressHandler()
        checkAndShowAd()
        loadSimulation()
    }
    
    private fun checkAndShowAd() {
        // Giriş sayacını artır
        val launchCount = sharedPreferences.getInt(KEY_LAUNCH_COUNT, 0) + 1
        sharedPreferences.edit().putInt(KEY_LAUNCH_COUNT, launchCount).apply()
        
        // Her 3 girişte bir reklam göster
        if (launchCount % AD_SHOW_INTERVAL == 0) {
            loadInterstitialAd()
        }
    }
    
    private fun loadInterstitialAd() {
        val adRequest = AdRequest.Builder().build()
        
        InterstitialAd.load(
            this,
            INTERSTITIAL_AD_UNIT_ID,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    showInterstitialAd()
                }
                
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    interstitialAd = null
                    android.util.Log.d("Ads", "Interstitial ad failed to load: ${loadAdError.message}")
                }
            }
        )
    }
    
    private fun showInterstitialAd() {
        interstitialAd?.let { ad ->
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    interstitialAd = null
                    // Reklam kapandıktan sonra bir sonraki reklamı önceden yükle
                    loadInterstitialAd()
                }
                
                override fun onAdFailedToShowFullScreenContent(p0: com.google.android.gms.ads.AdError) {
                    interstitialAd = null
                    android.util.Log.d("Ads", "Interstitial ad failed to show: ${p0.message}")
                }
                
                override fun onAdShowedFullScreenContent() {
                    android.util.Log.d("Ads", "Interstitial ad showed")
                }
            }
            
            ad.show(this)
        }
    }
    
    private fun setupBackPressHandler() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    finish()
                }
            }
        })
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        val settings = webView.settings

        // Performans optimizasyonları
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        
        // Dosya erişimi (assets klasöründen JS/CSS yüklemek için gerekli)
        settings.allowFileAccess = true
        settings.allowContentAccess = true
        @Suppress("DEPRECATION")
        settings.allowFileAccessFromFileURLs = true
        @Suppress("DEPRECATION")
        settings.allowUniversalAccessFromFileURLs = true
        settings.mediaPlaybackRequiresUserGesture = false
        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        
        // WebView performans ayarları
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        webView.isHorizontalScrollBarEnabled = false
        webView.isVerticalScrollBarEnabled = false
        webView.overScrollMode = View.OVER_SCROLL_NEVER

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String?, favicon: android.graphics.Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progressBar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView, url: String?) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.GONE
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val url = request?.url?.toString() ?: return false
                val isMainFrame = request?.isForMainFrame ?: false
                
                // Reklam URL'lerini ve iframe'leri WebView içinde aç (tıklamaları engelleme)
                val adDomains = listOf(
                    "googleads", "doubleclick", "googlesyndication", "adservice",
                    "adsense", "advertising", "adnxs", "adform", "advertising.com",
                    "adsrvr", "adtech", "advertising", "pubmatic", "rubiconproject",
                    "openx", "indexexchange", "criteo", "outbrain", "taboola"
                )
                
                val isAdUrl = adDomains.any { url.contains(it, ignoreCase = true) }
                
                // Ana frame değilse (iframe, reklam vb.) WebView içinde aç
                if (!isMainFrame || isAdUrl) {
                    return false // WebView içinde aç
                }
                
                return when {
                    // Instagram URL'leri - Instagram uygulamasında aç
                    url.contains("instagram.com", ignoreCase = true) -> {
                        try {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            intent.setPackage("com.instagram.android")
                            startActivity(intent)
                            true
                        } catch (e: Exception) {
                            // Instagram yüklü değilse Chrome'da aç
                            try {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                intent.setPackage("com.android.chrome")
                                startActivity(intent)
                                true
                            } catch (e2: Exception) {
                                // Chrome da yoksa varsayılan tarayıcıda aç
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                startActivity(intent)
                                true
                            }
                        }
                    }
                    
                    // Mail linkleri - Gmail uygulamasında aç
                    url.startsWith("mailto:", ignoreCase = true) -> {
                        try {
                            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse(url))
                            intent.setPackage("com.google.android.gm")
                            startActivity(intent)
                            true
                        } catch (e: Exception) {
                            // Gmail yüklü değilse varsayılan mail uygulamasında aç
                            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse(url))
                            startActivity(intent)
                            true
                        }
                    }
                    
                    // Diğer URL'ler - Chrome'da aç (sadece ana frame için)
                    url.startsWith("http://", ignoreCase = true) || 
                    url.startsWith("https://", ignoreCase = true) -> {
                        try {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            intent.setPackage("com.android.chrome")
                            startActivity(intent)
                            true
                        } catch (e: Exception) {
                            // Chrome yoksa varsayılan tarayıcıda aç
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            startActivity(intent)
                            true
                        }
                    }
                    
                    // Diğer durumlar için WebView'de aç
                    else -> false
                }
            }
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                consoleMessage?.let {
                    android.util.Log.d("WebView", it.message())
                }
                return true
            }
        }
    }

    private fun loadSimulation() {
        webView.loadUrl("file:///android_asset/index.html")
    }

    override fun onPause() {
        super.onPause()
        webView.onPause()
        webView.pauseTimers()
    }

    override fun onResume() {
        super.onResume()
        webView.onResume()
        webView.resumeTimers()
    }

    override fun onDestroy() {
        webView.destroy()
        super.onDestroy()
    }
}
