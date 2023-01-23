package me.richo.cabtraining.view_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

import me.richo.cabtraining.R;

/**
 * Halaman Tutorial
 */
public class TutorialPage extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inisialisasi Konten Tutorial
        View view = inflater.inflate(R.layout.fragment_tutorial_page, container, false);
        WebView webView = view.findViewById(R.id.web_view);
        webView.loadUrl("file:///android_asset/index.html");
        return view;
    }
}