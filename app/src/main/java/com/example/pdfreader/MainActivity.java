package com.example.pdfreader;

import android.content.Intent;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        final Runnable r = new Runnable() {
            public void run() {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
                finish();
            }
        };

        Handler handler = new Handler();

        handler.postDelayed(r, 3000);


//        PDFView pdfView = (PDFView) findViewById(R.id.pdfView);
//
//        pdfView.fromAsset("test.pdf")
////                .pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
//                .enableSwipe(true) // allows to block changing pages using swipe
//                .swipeHorizontal(false)
//                .enableDoubletap(true)
//                .defaultPage(0)
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
//                .autoSpacing(false) // add dynamic spacing to fit each page on its own on the screen
////                .linkHandler(DefaultLinkHandler)
//                .pageFitPolicy(FitPolicy.WIDTH) // mode to fit pages in the view
//                .fitEachPage(false) // fit each page to the view, else smaller pages are scaled relative to largest page.
//                .pageSnap(false) // snap pages to screen boundaries
//                .pageFling(false) // make a fling change only a single page like ViewPager
//                .nightMode(false) // toggle night mode
//                .load();

    }
}