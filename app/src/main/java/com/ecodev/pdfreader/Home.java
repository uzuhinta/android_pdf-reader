package com.ecodev.pdfreader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.example.pdfreader.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;

public class Home extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener {

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
}