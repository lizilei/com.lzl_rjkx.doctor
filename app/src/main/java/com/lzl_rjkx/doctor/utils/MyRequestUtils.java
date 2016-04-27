package com.lzl_rjkx.doctor.utils;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by lzl_os on 16/3/16.
 * 网络请求数据类，图片下载类
 */
public class MyRequestUtils {
    private static Object object;

    private static Handler handler = new Handler();

    public final static void requestAsObject(final RequestParams params, final Object obj) {
        object = obj;
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                object = new Gson().fromJson(result, object.getClass());
                EventBus.getDefault().post(object);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

    public final static void request(final RequestParams params, final Object obj, final int what) {
        object = obj;
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                object = new Gson().fromJson(result, object.getClass());
                Message message = Message.obtain();
                message.obj = object;
                message.what = what;
                message.sendToTarget();
                handler.sendMessage(message);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }
}
