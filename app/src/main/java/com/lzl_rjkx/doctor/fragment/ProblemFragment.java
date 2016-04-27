package com.lzl_rjkx.doctor.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.lzl_rjkx.doctor.R;

/**
 * Created by Administrator on 2016/4/1.
 */
public class ProblemFragment extends Fragment {

    private WebView mWebView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frag_problem, null);
        mWebView = (WebView) v.findViewById(R.id.mWebView);
        mWebView.loadUrl("file:///android_asset/problem.html");
        return v;
    }
}
