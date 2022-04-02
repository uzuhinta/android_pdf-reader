package com.example.pdfreader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class Home extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener {
//    ADS
    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712";
    private InterstitialAd interstitialAd;
    private static final String TAG = "Home";
//MAIN
    private int pageNumber = 0;
    private PDFView pdfView;
    int orientation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);

        pdfView = (PDFView) findViewById(R.id.pdfView);
        orientation = getResources().getConfiguration().orientation;

        pdfView.fromAsset("test.pdf")
                .defaultPage(pageNumber)
                .fitEachPage(true)
                .autoSpacing(true)
                .onPageChange(this)
                .onLoad(this)
                .enableAnnotationRendering(true)
                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(10) // in dp
                .pageFitPolicy(FitPolicy.WIDTH)
                .pageSnap(true)
                .pageFling(true)
                .load();

//        ADS

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });

        loadAd();

//        pdfView.fromAsset("test.pdf")
////                .pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
//                .enableSwipe(true) // allows to block changing pages using swipe
//                .swipeHorizontal(false)
//                .enableDoubletap(true)
//                .defaultPage(0)
//                .enableAnnotationRendering(true)
//                .scrollHandle(new DefaultScrollHandle(this))
//                // allows to draw something on the current page, usually visible in the middle of the screen
////                .onDraw(onDrawListener)
//                // allows to draw something on all pages, separately for every page. Called only for visible pages
////                .onDrawAll(onDrawListener)
////                .onLoad(onLoadCompleteListener) // called after document is loaded and starts to be rendered
////                .onPageChange(onPageChangeListener)
////                .onPageScroll(onPageScrollListener)
////                .onError(onErrorListener)
////                .onPageError(onPageErrorListener)
////                .onRender(onRenderListener) // called after document is rendered for the first time
//                // called on single tap, return true if handled, false to toggle scroll handle visibility
////                .onTap(onTapListener)
////                .onLongPress(onLongPressListener)
//                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
//                .password(null)
//                .scrollHandle(null)
//                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
//                // spacing between pages in dp. To define spacing color, set view background
//                .spacing(0)
//                .autoSpacing(true) // add dynamic spacing to fit each page on its own on the screen
////                .linkHandler(DefaultLinkHandler)
//                .pageFitPolicy(FitPolicy.BOTH) // mode to fit pages in the view
//                .fitEachPage(false) // fit each page to the view, else smaller pages are scaled relative to largest page.
//                .pageSnap(true) // snap pages to screen boundaries
//                .pageFling(false) // make a fling change only a single page like ViewPager
//                .nightMode(false) // toggle night mode
//                .load();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        System.out.println(String.format("%s / %s", pageNumber + 1, pageCount));
//        ADS
        if (page == 2 | page == 10) {
            showInterstitial();
        }
        if (interstitialAd == null) {
            System.out.println("LOAD ADS");
            loadAd();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        System.out.println("onSaveInstanceState" + pageNumber);
        outState.putInt("currentPage", pageNumber);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        System.out.println("onRestoreInstanceState" + savedInstanceState.getInt("currentPage"));
        pageNumber = +savedInstanceState.getInt("currentPage");
        System.out.println("onRestoreInstanceState" + pageNumber);
    }

    @Override
    public void loadComplete(int nbPages) {
//        System.out.println("pageNumber" + pageNumber);

        pdfView.jumpTo(pageNumber, true);
        pdfView.setMinZoom((float) 0.4);

    }

    public void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(
                this,
                AD_UNIT_ID,
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        Home.this.interstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                        Toast.makeText(Home.this, "onAdLoaded()", Toast.LENGTH_SHORT).show();
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        Home.this.interstitialAd = null;
                                        Log.d("TAG", "The ad was dismissed.");
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        Home.this.interstitialAd = null;
                                        Log.d("TAG", "The ad failed to show.");
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when fullscreen content is shown.
                                        Log.d("TAG", "The ad was shown.");
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        interstitialAd = null;

                        String error =
                                String.format(
                                        "domain: %s, code: %d, message: %s",
                                        loadAdError.getDomain(), loadAdError.getCode(), loadAdError.getMessage());
                        Toast.makeText(
                                Home.this, "onAdFailedToLoad() with error: " + error, Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (interstitialAd != null) {
            interstitialAd.show(this);
        } else {
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
        }
    }
}