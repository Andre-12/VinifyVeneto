package com.example.vinifyveneto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

public class PdfActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf_layout);

        PDFView pdfView;

        pdfView=findViewById(R.id.pdfView);

        pdfView.fromAsset("privacy.pdf").defaultPage(0).scrollHandle(new DefaultScrollHandle(this)).spacing(10).load();


    }
}
