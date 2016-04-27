package com.lzl_rjkx.doctor.utils;

import android.app.Activity;
import android.widget.Toast;

public class ToastUtils {
    public static void showToast(final Activity context, final String msg) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (AppUtils.isFastClick()) {
                    return;
                } else {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
